package com.lksoft.astar.bytemap;

import com.lksoft.astar.pathfind.SquareBoard;
import com.lksoft.astar.pathfind.SquareNode;


/**
 * Create squareboard over a walk-area bytemap
 * 
 * @author lago francesco
 *
 */
public class SquareBoardBuilder {

    // Hash of visited nodes
    private boolean[] visited;

	/**
	 * Load a SquareBoard that fits all walkable area of the bytemap
	 *
	 * @param byteMap 8-bit walkable-area bitmap
	 * @param squareSize square-size
	 * @return The resulting squareboard
	 */
	public SquareBoard createSquareBoardFromCollision(ByteMap byteMap, int squareSize) {
        // New squareboard
		SquareBoard squareBoard = new SquareBoard(squareSize);

        // Visited nodes hash
        visited = new boolean[byteMap.getWidth() * byteMap.getHeight()];

        for( int x=0; x<byteMap.getWidth(); x+=3 ){
            for( int y=0; y<byteMap.getHeight(); y+=3 ){
                if( byteMap.getPixel(x, y) != 0 ){
                    int areaId = byteMap.getPixel(x, y);
                    int ordx = x/squareBoard.getSquareSize();
                    int ordy = y/squareBoard.getSquareSize();
                    SquareNode newNode = new SquareNode(ordx, ordy, squareBoard.getSquareSize(), areaId);
                    squareBoard.addSquare(newNode);
                }
            }
        }

        return squareBoard;
	}
}
