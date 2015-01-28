package com.lksoft.astar.pathfind;


import java.io.Serializable;

/**
 * A*用の四角いマス
 * 隣の数は任意です。
 * 
 * @author lago francesco
 *
 */

public class SquareNode implements Serializable {
    private static final long serialVersionUID = -7346468575663065410L;
	
	private int ordX;
	private int ordZ;
	private FRect bounds;
    private int areaId;


	/**
	 * ﾏｽ座標から生成
	 * @param ordX ﾏｽ座標
	 * @param ordZ ﾏｽ座標
	 * @param squareSize
	 */
	public SquareNode( int ordX, int ordZ, float squareSize ) {
		this.ordX = ordX;
		this.ordZ = ordZ;

		// ﾊﾞｳﾝﾄﾞ
		bounds = new FRect( ordX * squareSize, ordZ * squareSize, squareSize, squareSize );
	}
	
	/**
	 * コンストラクタ.
	 * @param ordX ﾏｽ座標
	 * @param ordZ ﾏｽ座標
	 * @param squareSize
	 * @param areaId
	 */
	public SquareNode( int ordX, int ordZ, float squareSize, int areaId ) {
		this( ordX, ordZ, squareSize );
		this.areaId = areaId;
	}
	public int getOrdX() {
		return ordX;
	}
	public int getOrdZ() {
		return ordZ;
	}
	public FRect getBounds() {
		return bounds;
	}
    public int getAreaId() {
        return areaId;
    }

	@Override
	public String toString(){
		return "square("+ordX+","+ordZ+"): "+bounds;
	}

}
