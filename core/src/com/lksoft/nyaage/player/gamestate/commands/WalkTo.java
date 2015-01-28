package com.lksoft.nyaage.player.gamestate.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.lksoft.astar.pathfind.AStarPathFinder;
import com.lksoft.astar.pathfind.AStarPathFinder.Point;
import com.lksoft.astar.pathfind.SquareBoard;
import com.lksoft.astar.pathfind.SquareNode;
import com.lksoft.nyaage.player.Nya;
import com.lksoft.nyaage.player.common.Direction;
import com.lksoft.nyaage.player.gamestate.CharacterState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lake on 14/12/25.
 */
public class WalkTo extends ScriptCommand {
    // Path
    List<SquareNode> path;
    // Timer
    float timer = 0.0f;

    // Character
    CharacterState character;

    // Total walk time
    float walkTime;

    /**
     * Go to destination!
     * @param destination The destination
     */
    public WalkTo(SquareBoard sqb, boolean[] walkAreasStatus, Vector2 destination, CharacterState character){
        this.character = character;

        path = AStarPathFinder.get().findGoodPath(
                new Point((int) character.getPosition().x, (int) character.getPosition().y),
                new Point((int) destination.x, (int) destination.y),
                sqb,
                3.0f,
                walkAreasStatus
        );

        // Make the path as straight as possible
        simplifyPath(sqb, walkAreasStatus);

        // Calculate path length
        float pathLength = 0;
        for( int i=1; i<path.size(); i++ ){
            float dx = path.get(i).getBounds().getCenterX() - path.get(i-1).getBounds().getCenterX();
            float dy = path.get(i).getBounds().getCenterY() - path.get(i-1).getBounds().getCenterY();
            pathLength += Math.sqrt(dx*dx + dy*dy);
        }

        walkTime = pathLength/character.getCharacter().getWalkingSpeed();
    }

    /**
     * Straighten up the path
     */
    private void simplifyPath(SquareBoard sqb, boolean[] walkAreaStatus) {
        List<SquareNode> toDelete = new ArrayList<>();

        // Take the first node as key node
        int lastKeyNode = 0;

        while(true){
            // Search next key node, which is a node
            // you cannot go by a straight line
            int i;
            for( i=path.size()-1; i>lastKeyNode; i-- ){
                if( canGoStraight(sqb, walkAreaStatus, lastKeyNode, i) ) {
                    // Schedule all intermediate nodes for deletion
                    for( int d=lastKeyNode+1; d<i; d++ ){
                        toDelete.add(path.get(d));
                    }

                    lastKeyNode = i;
                    break;
                }
            }
            // End
            if( lastKeyNode == path.size()-1 ) break;
        }

        // Delete intermediate nodes!
        for( SquareNode n : toDelete ){
            path.remove(n);
        }
    }

    /**
     * Determine if there is a straight path from node1 to node2
     * @param node1
     * @param node2
     * @return
     */
    private boolean canGoStraight(SquareBoard sqb, boolean[] walkAreaStatus, int node1, int node2) {
        SquareNode n1 = path.get(node1);
        SquareNode n2 = path.get(node2);

        // Ray trace
        float dx = n1.getBounds().getCenterX() - n2.getBounds().getCenterX();
        float dy = n1.getBounds().getCenterY() - n2.getBounds().getCenterY();
        float dist = (float)Math.sqrt(dx*dx + dy*dy);
        int steps = (int)(dist/n1.getBounds().getWidth());

        // Check with square width sensibility
        for( float t=0; t<1.0f; t+=1.0f/steps ){
            float checkx = Interpolation.linear.apply(n1.getBounds().getCenterX(), n2.getBounds().getCenterX(), t);
            float checky = Interpolation.linear.apply(n1.getBounds().getCenterY(), n2.getBounds().getCenterY(), t);
            SquareNode node = sqb.getSquareAbsolute((int)checkx, (int)checky);
            if( node == null || !walkAreaStatus[node.getAreaId()] ) return false;
        }
        return true;
    }

    /**
     * Update frame
     */
    public boolean doUpdate(){
        timer += Gdx.graphics.getDeltaTime();

        // Current walked length
        float curLength = character.getCharacter().getWalkingSpeed()*timer;

        // Find current node
        float pathLength = 0;
        int i=1;
        for( ; i<path.size(); i++ ){
            float dx = path.get(i).getBounds().getCenterX() - path.get(i-1).getBounds().getCenterX();
            float dy = path.get(i).getBounds().getCenterY() - path.get(i-1).getBounds().getCenterY();
            pathLength += Math.sqrt(dx*dx + dy*dy);
            if( curLength < pathLength ) break;
        }

        // Finish
        if( i==path.size() ) {
            character.getPosition().set(
                    path.get(i-1).getBounds().getCenterX(),
                    path.get(i-1).getBounds().getCenterY());
            return true;
        }

        // Target node is 'i'
        // Find t
        float dx = path.get(i).getBounds().getCenterX() - path.get(i-1).getBounds().getCenterX();
        float dy = path.get(i).getBounds().getCenterY() - path.get(i-1).getBounds().getCenterY();
        float nodeDist = (float) Math.sqrt(dx*dx + dy*dy);
        float t = (curLength - (pathLength - nodeDist)) / nodeDist;

        // Interpolate
        float ix = Interpolation.linear.apply(
                path.get(i-1).getBounds().getCenterX(),
                path.get(i).getBounds().getCenterX(),
                t);
        float iy = Interpolation.linear.apply(
                path.get(i-1).getBounds().getCenterY(),
                path.get(i).getBounds().getCenterY(),
                t);
        character.getPosition().set(ix, iy);

        // Set direction
        if( Math.abs(dx) > Math.abs(dy) && dx > 0 ) character.faceDirection(Direction.RIGHT);
        if( Math.abs(dx) > Math.abs(dy) && dx < 0 ) character.faceDirection(Direction.LEFT);
        if( Math.abs(dy) > Math.abs(dx) && dy > 0 ) character.faceDirection(Direction.UP);
        if( Math.abs(dy) > Math.abs(dx) && dy < 0 ) character.faceDirection(Direction.DOWN);

        return false;
    }

    @Override
    public void draw() {
        if( Nya.get().getDebugState().isDrawGizmos() ){
            drawDebug();
        }
    }

    /**
     * Draw the path
     */
    public void drawDebug() {
        Nya.get().getSpriteBatch().end();
        Nya.get().getShapeRenderer().setProjectionMatrix(Nya.get().getViewport().getCamera().combined);
        Nya.get().getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
        // Draw grid
        for( SquareNode node : Nya.get().getGameState().getCurrentRoom().getSquareboard().getAllSquares() ){
            Nya.get().getShapeRenderer().rect(
                    node.getBounds().getLeft(),
                    node.getBounds().getTop(),
                    node.getBounds().getWidth(),
                    node.getBounds().getHeight()
                    );
        }

        // Draw path
        for( int i=1; i<path.size(); i++ ){
            Nya.get().getShapeRenderer().setColor(255.0f, 0, 0, 1);
            Nya.get().getShapeRenderer().line(
                    path.get(i-1).getBounds().getCenterX(),
                    path.get(i-1).getBounds().getCenterY(),
                    path.get(i).getBounds().getCenterX(),
                    path.get(i).getBounds().getCenterY()
            );
        }
        Nya.get().getShapeRenderer().end();
        Nya.get().getSpriteBatch().begin();
    }

    @Override
    public void doSkip() {
        // Position
        int dx = (int)path.get(path.size()-1).getBounds().getCenterX();
        int dy = (int)path.get(path.size()-1).getBounds().getCenterY();
        character.getPosition().set(dx, dy);

        // Face
        if( path.size() > 2 ) {
            int ndx = dx - (int) path.get(path.size() - 2).getBounds().getCenterX();
            int ndy = dy - (int) path.get(path.size() - 2).getBounds().getCenterY();

            if( Math.abs(ndx) > Math.abs(ndy) ){
                if( ndx > 0 ) character.faceDirection(Direction.RIGHT);
                else character.faceDirection(Direction.LEFT);
            } else {
                if( ndy > 0 ) character.faceDirection(Direction.UP);
                else character.faceDirection(Direction.DOWN);
            }
        }
    }

    @Override
    public void onFinish() {
        character.endWalking();
    }
}
