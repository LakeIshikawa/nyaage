package com.lksoft.nyaage.desktop;

import com.badlogic.gdx.tools.FileProcessor;
import com.lksoft.astar.bytemap.ByteMap;
import com.lksoft.astar.bytemap.SquareBoardBuilder;
import com.lksoft.astar.pathfind.SquareBoard;
import com.lksoft.astar.pathfind.SquareBoardIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by lake on 14/12/27.
 *
 * Packer that constructs sqb maps from bytemaps
 */
public class WalkmapPacker {

    /**
     * Converts all bytemaps in path to squareboards and copy them to destPath
     * @param path
     * @param destPath
     */
    public static void process(String path, String destPath) throws Exception {
        WalkmapPackerFileProcessor processor = new WalkmapPackerFileProcessor();
        processor.process(path, destPath);
    }

    /**
     * File processor
     */
    private static class WalkmapPackerFileProcessor extends FileProcessor {
        // Construct
        public WalkmapPackerFileProcessor(){
            setFlattenOutput(true);
            addInputSuffix(".bmp");
        }

        // Process file
        protected void processFile (Entry entry) throws Exception {
            System.out.print("Processing walkmap:"+entry.inputFile.getName()+" ...");
            // Load bytemap
            FileInputStream fis = new FileInputStream(entry.inputFile);
            byte[] bmap = new byte[(int)entry.inputFile.length()];
            fis.read(bmap);
            fis.close();

            ByteMap map = new ByteMap(bmap);

            // Make sqb
            SquareBoardBuilder builder = new SquareBoardBuilder();
            SquareBoard sqb = builder.createSquareBoardFromCollision(map, 5);

            // Save it
            String newname = entry.inputFile.getName().replace(".bmp", ".sqb");
            File newfile = new File(entry.outputDir, newname);
            entry.outputDir.mkdirs();
            newfile.createNewFile();

            FileOutputStream fos = new FileOutputStream(newfile);
            SquareBoardIO.saveSQB(sqb, fos);
            fos.close();

            System.out.println("done.");
        }
    }
}
