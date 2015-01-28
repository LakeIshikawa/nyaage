package com.lksoft.astar.pathfind;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * .sqbファイルからスクエアボードデータをロードする機能
 * 
 * @author lago francesco
 *
 */
public class SquareBoardIO {

	/**
	 * .sqbファイルからスクエアボードデータをロードする
	 * 
	 * @param sqbResource .sqbファイルを示すファイルリソース
	 * @return 展開されたスクエアボード
	 */
	public static SquareBoard loadSQB(InputStream sqbResource){
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(sqbResource);
			SquareBoard squareBoard = (SquareBoard) ois.readObject();

			return squareBoard;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

    /**
     * Save squareboard to stream
     * @param sqb
     * @param sqbOutput
     */
    public static void saveSQB(SquareBoard sqb, OutputStream sqbOutput){
        ObjectOutputStream oos = null;
        try{
            oos = new ObjectOutputStream(sqbOutput);
            oos.writeObject(sqb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
