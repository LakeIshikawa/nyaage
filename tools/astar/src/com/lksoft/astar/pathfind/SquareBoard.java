package com.lksoft.astar.pathfind;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * マスﾎﾞｰﾄﾞ
 * 移動できるマスの構造を表すﾃﾞｰﾀです。
 * 
 * @author lago francesco
 *
 */
public class SquareBoard implements Serializable{
	private static final long serialVersionUID = -7084396938763065410L;

	// ﾉｰﾄﾞのﾏｯﾌﾟ
	private HashMap<Integer, SquareNode> nodeMap = new HashMap<>();
	
	// 四角の幅
	private int squareSize = 2;

    // Maximum map width
    private int MAX_WIDTH = 2048;

	
	/**
	 * 四角のｻｲｽﾞを設定
	 * @param squareSize メートル
	 */
	public SquareBoard(int squareSize){
		this.squareSize = squareSize;
	}
	
	/**
	 * 指定の設置座標（マス単位）に、指定のﾉｰﾄﾞを追加
	 * 
	 * @param squareNode 追加する四角
	 */
	public void addSquare(SquareNode squareNode){
		nodeMap.put(squareNode.getOrdX() + squareNode.getOrdZ()*MAX_WIDTH, squareNode);
	}
	
	/**
	 * 指定の座標（マス単位）に設置されてるﾉｰﾄﾞを取得する
	 * 
	 * @param ordX 指定座標
	 * @param ordZ 指定座標
	 * @return 四角
	 */
	public SquareNode getSquare(int ordX, int ordZ){
		return nodeMap.get(ordX + ordZ*MAX_WIDTH);
	}

	/**
	 * @return 一マスの辺の長さ
	 */
	public int getSquareSize() {
		return squareSize;
	}

    /**
     * Get the square nearest to point
     * @param x
     * @param z
     * @return
     */
    public SquareNode getSquareSurrounding(int x, int z, boolean[] reachability){
        SquareNode node = getSquareAbsolute(x, z);
        if( node != null && reachability[node.getAreaId()] ) {
            return node;
        }

        // 一番近いﾏｽを探す
        return rayTraceSearch(x, z, 10000.0f, (float) (Math.PI/8), reachability);
    }

    /**
     * Ray trace from point to nearest square
     * @param x
     * @param z
     * @param searchDistance
     * @param searchAngle
     * @param reachability
     * @return
     */
    private SquareNode rayTraceSearch(float x, float z, float searchDistance, float searchAngle, boolean[] reachability) {
        for( float step=getSquareSize()/2; step<=searchDistance; step+=getSquareSize()){
            for(float rayAngle = 0.0f; rayAngle <= Math.PI*2; rayAngle+=searchAngle ){
                float search_x = x + (float) (step*Math.cos(rayAngle));
                float search_y = z + (float) (step*Math.sin(rayAngle));
                int ordx = (int)search_x/squareSize;
                int ordy = (int)search_y/squareSize;
                SquareNode candidate = nodeMap.get(ordx + ordy*MAX_WIDTH);

                if( candidate != null && reachability[candidate.getAreaId()]) {
                    return candidate;
                }
            }
        }

        return null;
    }

    /**
     * Get the square at absolute position
     * @param x
     * @param y
     * @return
     */
    public SquareNode getSquareAbsolute(int x, int y){
        int ordx = x/squareSize;
        int ordy = y/squareSize;
        return nodeMap.get(ordx + ordy*MAX_WIDTH);
    }
	
	/**
	 * 指定四角の隣接ﾘｽﾄ取得
     * @param node 指定四角
     * @param outNeighbourghsList 指定四角の隣接ﾘｽﾄ
     * @param reachability
     */
	public synchronized void getNeighbourghsOf(SquareNode node, List<SquareNode> outNeighbourghsList, boolean[] reachability){
		outNeighbourghsList.clear();
		
		for( int dx = -1; dx<=1; dx++ ){
			for( int dy = -1; dy<=1; dy++ ){
				SquareNode neighbourgh = getSquare(node.getOrdX()+dx, node.getOrdZ()+dy);
				if( neighbourgh != null && reachability[neighbourgh.getAreaId()] ) {
					outNeighbourghsList.add(neighbourgh);
				}
			}
		}
	}
	
    /**
	 * 全ての四角を頂く
	 * @return 全ての四角
	 */
	public Collection<SquareNode> getAllSquares() {
		return nodeMap.values();
	}
	
	@Override
	public String toString(){
		StringBuilder b = new StringBuilder();
		for( SquareNode node : nodeMap.values() ){
			b.append(node.toString()+"\n");
		}
		
		return b.toString();
	}
}
