package com.lksoft.astar.pathfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Weighted A*を実装する機能
 * 
 * @author lago francesco
 *
 */
public class AStarPathFinder {
	private static AStarPathFinder self = new AStarPathFinder();
	public static AStarPathFinder get() { return self; }

    // A simple point
    public static class Point {
        public int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

	
	HashSet<SquareNode> closedSet = new HashSet<SquareNode>();
	HashSet<SquareNode> openSet = new HashSet<SquareNode>();
	
	HashMap<SquareNode, SquareNode> cameFrom = new HashMap<SquareNode, SquareNode>();
	HashMap<SquareNode, Float> h_score = new HashMap<SquareNode, Float>();
	HashMap<SquareNode, Float> g_score = new HashMap<SquareNode, Float>();
	private List<SquareNode> neighbourghList  = new ArrayList<SquareNode>();
	
	/**
	 * Weighted A*を実行します。
	 * start座標に一番近いﾏｽから、goalの一番近いﾏｽまでにいけるための
	 * 「まあまあ短い」ルートを検索します。
	 * weightの設定で制度/処理速度のバランスが決まる。
	 * 
	 * weightが1.0以下の場合は、必ず「最短ルート」が検出されますが、処理が一番重い。
	 * weightが1.0以上の場合、必ず「最短ルートの長さ * weight」より短いﾊﾟｽが検出されます。　処理がweightが多ければ多いほど、早い。
	 * 
	 * @param start ｽﾀｰﾄ地点
	 * @param goal ゴール地点
	 * @param collisionBoard ｺﾘｼﾞｮﾝﾎﾞｰﾄﾞ
	 * @param weight 1.0~
	 * @return 四角のﾘｽﾄで表したstartからgoalまでのパス
	 */
	public List<SquareNode> findGoodPath(Point start, Point goal, SquareBoard collisionBoard, float weight, boolean[] reachability){
		final float diagonal_distance = (float) (collisionBoard.getSquareSize()*Math.sqrt(2.0f));
		int loopNum = 0;
		
		// 初期化
		closedSet.clear();
		openSet.clear();
		cameFrom.clear();
		h_score.clear();
		g_score.clear();
		
		//移動開始マスを取得（線コリジョンとかぶっていてもOK）
		SquareNode startNode = collisionBoard.getSquareSurrounding(start.x, start.y, reachability);
		//ゴールマスを取得（線コリジョンとかぶってはいけない）
		SquareNode goalNode = collisionBoard.getSquareSurrounding(goal.x, goal.y, reachability);
		
		if ((startNode == null) || (goalNode == null)) {
			// スタート or ゴールのノード無しならルートはありませんよ.
			return null;
		}
		
		openSet.add(startNode);
		g_score.put(startNode, 0.0f);
		h_score.put(startNode, h(startNode, goalNode, weight));
		
		while(!openSet.isEmpty()){			
			SquareNode current = findBest(openSet, h_score, g_score);
			
			// 終了！
			if( current == goalNode ){
				List<SquareNode> path = new ArrayList<SquareNode>();
				reconstructPath(cameFrom, current, path );

				return path;
			}
			
			openSet.remove(current);
			closedSet.add(current);
			
			// Get neighbors of node
			collisionBoard.getNeighbourghsOf(current, neighbourghList, reachability);

			for( SquareNode neighbourgh : neighbourghList ){
				if( closedSet.contains(neighbourgh) ) continue;
				
				float tentative_g_score = g_score.get(current) + distance_between(current, neighbourgh, diagonal_distance, collisionBoard.getSquareSize());
				
				boolean tentative_is_better = false;
				if( !openSet.contains(neighbourgh) ){
					openSet.add(neighbourgh);
					h_score.put(neighbourgh, h(neighbourgh, goalNode, weight));
					tentative_is_better = true;
				}
				else if(tentative_g_score < g_score.get(neighbourgh)){
					tentative_is_better = true;
				}
				
				if( tentative_is_better ){
					cameFrom.put(neighbourgh, current);
					g_score.put(neighbourgh, tentative_g_score);
				}
			}
			
			loopNum++;
		}
		
		// ルートはありませんでした。
		return null;
	}

	/**
	 * A*を実行します。
	 * start座標に一番近いﾏｽから、goalの一番近いﾏｽまでにいけるための
	 * 「最短」ルートを検索します。
	 * 
	 * @param start ｽﾀｰﾄ地点
	 * @param goal ゴール地点
	 * @param collisionBoard ｺﾘｼﾞｮﾝﾎﾞｰﾄﾞ
	 * @return 四角のﾘｽﾄで表したstartからgoalまでのパス
	 */
	public List<SquareNode> findShortestPath(Point start, Point goal, SquareBoard collisionBoard, boolean[] reachability){
		return findGoodPath(start, goal, collisionBoard, 1.0f, reachability);
	}
	
	/**
	 * 距離ﾋｭｰﾘｽﾃｨｯｸ
	 * 直線距離にweightを掛けた値が想定距離になります
	 * 
	 * @param start ここから
	 * @param goal ここまで
	 * @param weight このウエイトを掛けて
	 * @return startからgoalの想定距離
	 */
	private static final float h(SquareNode start, SquareNode goal, float weight) {
		float d1 = start.getBounds().getCenterX() - goal.getBounds().getCenterX();
        float d2 = start.getBounds().getCenterY() - goal.getBounds().getCenterY();
        return (float)Math.sqrt(d1*d1+d2*d2)*weight;
	}
	

	/**
	 * 指定のセットに指定のマッピングで一番スコアの低いものを選ぶ
	 * 
	 * @param set 指定のセット
	 * @param hScore 各四角に対するhスコア
	 * @param gScore 各四角に対するgスコア
	 * @return 指定のセットの中で、一番合計スコアの低い四角
	 */
	private static final SquareNode findBest(HashSet<SquareNode> set, HashMap<SquareNode, Float> hScore, HashMap<SquareNode, Float> gScore) {
		SquareNode best = null;
		float bestCost = Float.MAX_VALUE;
		
		for( SquareNode node : set ){
			float f_cost = hScore.get(node) + gScore.get(node);
			if( best == null || f_cost < bestCost ){
				best = node;
				bestCost = f_cost;
			}
		}
		
		return best;
	}
	
	/**
	 * 指定のﾉｰﾄﾞまでのﾊﾟｽを作り戻す
	 * 
	 * @param cameFrom 各四角の「ペアレント」を示すマップ
	 * @param currentNode ここまでのパスを再構築する
	 * @param path 出力：　currentNodeとそのペアレント全部がここに追加されます 
	 */
	private static final void reconstructPath(HashMap<SquareNode, SquareNode> cameFrom, SquareNode currentNode, List<SquareNode> path) {
		path.add(0, currentNode);
		
		SquareNode parentNode = cameFrom.get(currentNode);
		if( parentNode != null ) reconstructPath(cameFrom, parentNode, path);
	}
	
	/**
	 * 隣接移動コスト
	 * 
	 * @param current この四角から
	 * @param neighbourgh この四角まで
	 * @param diagonal_distance 斜め移動のコスト 
	 * @return currentからneighbourghまでの移動コスト
	 */
	private static final Float distance_between(SquareNode current, SquareNode neighbourgh, float diagonal_distance, float perpendicular_distance) {
		// 斜め
		if( current.getOrdX() != neighbourgh.getOrdX() && current.getOrdZ() != neighbourgh.getOrdZ() ){
			return diagonal_distance;
		}
		else{
			return perpendicular_distance;
		}
	}
}
