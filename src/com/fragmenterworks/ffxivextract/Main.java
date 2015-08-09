package com.fragmenterworks.ffxivextract;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.fragmenterworks.ffxivextract.storage.HashDatabase;
import com.mysql.jdbc.Connection;
import com.fragmenterworks.ffxivextract.gui.FileManagerWindow;
import com.fragmenterworks.ffxivextract.gui.components.EXDF_View;
import com.fragmenterworks.ffxivextract.gui.components.Update_Dialog;
import com.fragmenterworks.ffxivextract.helpers.DatBuilder;
import com.fragmenterworks.ffxivextract.helpers.EXD_Searcher;
import com.fragmenterworks.ffxivextract.helpers.HavokNative;
import com.fragmenterworks.ffxivextract.helpers.LERandomAccessFile;
import com.fragmenterworks.ffxivextract.helpers.PathSearcher;
import com.fragmenterworks.ffxivextract.helpers.VersionUpdater;
import com.fragmenterworks.ffxivextract.helpers.VersionUpdater.VersionCheckObject;
import com.fragmenterworks.ffxivextract.models.EXHF_File;
import com.fragmenterworks.ffxivextract.models.SqPack_IndexFile;
import com.fragmenterworks.ffxivextract.models.SqPack_IndexFile.SqPack_Folder;

public class Main {

	public static void main(String[] args) {

		
		// Set to windows UI
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Init the hash database
		try {
			File dbFile = new File("./" + Constants.DBFILE_NAME);
			if (dbFile.exists())
				HashDatabase.init();
			else
				JOptionPane
						.showMessageDialog(
								null,
								Constants.DBFILE_NAME
										+ " is missing. No file or folder names will be shown... instead the file's hashes will be displayed.",
								"Hash DB Load Error", JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		/*
		HashDatabase.beginConnection();
		try {
			HashDatabase.globalConnection.setAutoCommit(false);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for (int j = 180; j <= 180; j++){
			
			
			if (j >= 120 && j <= 127)
				continue;
			if (j >= 150)
				continue;						
			
			for (int i = 0; i <= 999; i++)
			{
				
				HashDatabase.addPathToDB(String.format("ui/icon/%03d000/en/%03d%03d.tex", j, j, i), "060000", HashDatabase.globalConnection);
				HashDatabase.addPathToDB(String.format("ui/icon/%03d000/de/%03d%03d.tex", j, j, i), "060000", HashDatabase.globalConnection);
				HashDatabase.addPathToDB(String.format("ui/icon/%03d000/fr/%03d%03d.tex", j, j, i), "060000", HashDatabase.globalConnection);
				HashDatabase.addPathToDB(String.format("ui/icon/%03d000/ja/%03d%03d.tex", j, j, i), "060000", HashDatabase.globalConnection);
				System.out.println(String.format("ui/icon/%03d000/en/%03d%03d.tex", j, j, i));
			}			
			try {
				HashDatabase.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		HashDatabase.closeConnection();
		
		*/
		
	
		  
		/*
		for (int i = 0; i < 180; i++)
		{
			String folderName = String.format("ui/icon/%03d000",i);
	
				if (HashDatabase.computeCRC(folderName.getBytes(), 0, folderName.getBytes().length) == 0x670d2d02)
				{
					System.out.println("FOUND: " + folderName);
					break;
				}
			
		}
		*/
		/*
		String archive = "0a0000";
		HashDatabase.beginConnection();try{
		
			LERandomAccessFile ref = new LERandomAccessFile("E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\"+archive+".win32.index", "r");
	
			ref.seek(0x400+0x4+0x4+(0x48*0));
			int offset = ref.readInt();
			int size = ref.readInt();			
		
			System.out.println("Size= " + size);
			
			for (int i = 0; i < size/16; i++){				
				ref.seek(offset + (i * 16));
				int hash = ref.readInt();
				System.out.println(String.format("%d",hash));
				Statement statement = HashDatabase.globalConnection.createStatement();
				int query = statement
						.executeUpdate("update filenames set archive=\""+archive+"\" where hash=" + hash +";");
				
				if (query == 0)
				{
					Statement stm = HashDatabase.globalConnection.createStatement();
					stm.executeUpdate(String.format("insert or ignore into filenames (hash, used, archive, version) values(%d, 1, '%s', '%s')", hash, archive, Constants.DB_VERSION_CODE));
					stm.close();
				}
				
				statement.close();
			}
			
			ref.close();
		}
		catch (Exception e)
		{e.printStackTrace();}
		HashDatabase.closeConnection();		
		
		System.exit(1);*/
		// EXD_Searcher.findStains();

		// EXD_Searcher.openEveryModel();
		/*
		 * SqPack_IndexFile index = null; try { try { HashDatabase.connection =
		 * DriverManager .getConnection("jdbc:sqlite:./hashlist.db"); index =
		 * new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\0b0000.win32.index"
		 * ); index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\0c0000.win32.index"
		 * ); index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\010000.win32.index"
		 * ); System.out.println("Doing 2 next"); index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\020000.win32.index"
		 * ); index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\030000.win32.index"
		 * ); index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\040000.win32.index"
		 * ); System.out.println("Doing 5 next"); index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\050000.win32.index"
		 * ); index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\060000.win32.index"
		 * ); index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\070000.win32.index"
		 * ); System.out.println("Doing 8 next"); index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\080000.win32.index"
		 * ); HashDatabase.connection.close(); } catch (SQLException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * } catch (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 * 
		 * System.exit(0);
		 */

		// EXD_Searcher.getModelsFromModelChara("C:\\Users\\Filip\\Desktop\\exd\\modelchara.csv");

		/*
		 * try { PathSearcher.doPathSearch(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\060000.win32.index"
		 * ); } catch (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 * 
		 * /* SqPack_IndexFile index = null; try { index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\060000.win32.index"
		 * ); } catch (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 * 
		 * try{ Connection conn = HashDatabase.getConnection();
		 * conn.setAutoCommit(false);
		 * 
		 * for (SqPack_Folder folder : index.getPackFolders()) { String
		 * folderName = folder.getName(); if (!folderName.contains("ui/map/"))
		 * continue;
		 * 
		 * //Check if there is a unamed file boolean foundUnknown = false; for
		 * (int i = 0; i < folder.getFiles().length; i++) { if
		 * (!folder.getFiles()[i].getName().contains("m_m.tex")) { foundUnknown
		 * = true; break; } } if (!foundUnknown) continue;
		 * 
		 * String[] split = folderName.split("/");
		 * 
		 * 
		 * /* int foldernum = Integer.parseInt(split[2]);
		 * 
		 * for (int filenum = foldernum; filenum < foldernum + 999; filenum++){
		 * if (foldernum >= 20000 && foldernum <= 53000) {
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/" + String.format("%06d", filenum) + ".tex");
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/hq/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/hq/" + String.format("%06d", filenum) + ".tex");
		 * 
		 * } else if ((foldernum >= 150000 && foldernum <= 180000)) {
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/en/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/en/" + String.format("%06d", filenum) + ".tex");
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/de/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/de/" + String.format("%06d", filenum) + ".tex");
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/ja/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/ja/" + String.format("%06d", filenum) + ".tex");
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/fr/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/fr/" + String.format("%06d", filenum) + ".tex"); } else if
		 * ((foldernum >= 120000 && foldernum <= 127000)) {
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/en/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/en/" + String.format("%06d", filenum) + ".tex");
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/de/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/de/" + String.format("%06d", filenum) + ".tex");
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/ja/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/ja/" + String.format("%06d", filenum) + ".tex");
		 * HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/fr/" + String.format("%06d", filenum) + ".tex", conn);
		 * System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/fr/" + String.format("%06d", filenum) + ".tex");
		 * 
		 * } else{ //HashDatabase.addPathToDB("ui/icon/" + String.format("%06d",
		 * foldernum) + "/" + String.format("%06d", filenum) + ".tex", conn);
		 * //System.out.println("ui/icon/" + String.format("%06d", foldernum) +
		 * "/" + String.format("%06d", filenum) + ".tex"); }
		 * 
		 * } } conn.commit(); HashDatabase.closeConnection(conn);
		 * 
		 * } catch (Exception e){}
		 */
		/*
		 * EXD_Searcher.saveEXL();
		 * EXD_Searcher.createEXDFiles("./exddump2.txt");
		 * 
		 * try { HashDatabase.loadPathsFromTXT("./exddump2.txtout.txt");
		 * //return; } catch (SQLException e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); }
		 */
		/*
		 * 
		 * SqPack_IndexFile index; try { index = new SqPack_IndexFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\0a0000.win32.index"
		 * );
		 * 
		 * SqPack_DatFile dat = new SqPack_DatFile(
		 * "E:\\Program Files (x86)\\SquareEnix\\FINAL FANTASY XIV - A Realm Reborn\\game\\sqpack\\ffxiv\\0a0000.win32.dat0"
		 * ); EXD_Searcher.saveEXDNames(index, dat); } catch (IOException e1) {
		 * // TODO Auto-generated catch block e1.printStackTrace(); }
		 */

		// EXD_Searcher.saveEXL();
		/*
		 * for (int i = 0; i < 150; i++) {
		 * HashDatabase.addPathToDB(String.format
		 * ("chara/monster/m8%03d/obj/body/b0001/b0001.imc", i));
		 * HashDatabase.addPathToDB(String.format(
		 * "chara/monster/m8%03d/obj/body/b0001/model/m8%03db0001.mdl", i, i));
		 * }
		 */

		// Arguments
		if (args.length > 0) {
			
			// Info
			if (args.length == 1)
			{
				if (args[0].equals("-help"))
					System.out.println("Commands: -help, -debug, -pathsearch");
				else if (args[0].equals("-pathsearch"))
					System.out.println("Searches an archive for strings that start with <str>\n-pathsearch <path to index> <str>");
			}
			
			// DEBUG ON
			if (args[0].equals("-debug")) {
				Constants.DEBUG = true;
				System.out.println("Debug Mode ON");
			}

			// PATHSEARCH
			if (args[0].equals("-pathsearch")) {
				if (args.length < 3) {
					System.out
							.println("Not enough args.");
					return;
				}

				System.out
						.println("Starting Path Searcher (this will take a while)");

				
				try {
					PathSearcher.doPathSearch(args[1], args[2]);
				} catch (IOException e) {
					System.out
							.println("There was an error searching. Stacktrace: ");
					e.printStackTrace();
				}				

				return;
			}
		}

		// Open up the main window
		FileManagerWindow fileMan = new FileManagerWindow(Constants.APPNAME);
		fileMan.setVisible(true);

		// Load Prefs
		Preferences prefs = Preferences
				.userNodeForPackage(com.fragmenterworks.ffxivextract.Main.class);
		boolean firstRun = prefs.getBoolean(Constants.PREF_FIRSTRUN, true);		
		Constants.datPath = prefs.get(Constants.PREF_DAT_PATH, null);

		// First Run
		if (firstRun) {
			prefs.putBoolean(Constants.PREF_FIRSTRUN, false);

			int n = JOptionPane
					.showConfirmDialog(
							fileMan,
							"Would you like FFXIV Extractor to check for a new hash database?",
							"Hash DB Version Check", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				prefs.putBoolean(Constants.PREF_DO_DB_UPDATE, true);
			} else
				prefs.putBoolean(Constants.PREF_DO_DB_UPDATE, false);
		}

		// Version Check
		if (prefs.getBoolean(Constants.PREF_DO_DB_UPDATE, false)) {
			VersionCheckObject checkObj = VersionUpdater.checkForUpdates();

			if (HashDatabase.getHashDBVersion() < checkObj.currentDbVer
					|| Constants.APP_VERSION_CODE < checkObj.currentAppVer) {
				Update_Dialog updateDialog = new Update_Dialog(checkObj);
				updateDialog.setLocationRelativeTo(fileMan);
				updateDialog.setVisible(true);
			}
		}
			
	}
	
	private static void getMapPaths()
	{
		try {
			SqPack_IndexFile exdIndexFile = new SqPack_IndexFile(Constants.datPath + "\\game\\sqpack\\ffxiv\\0a0000.win32.index", true);
			EXHF_File exhfFile = new EXHF_File(exdIndexFile.extractFile("exd/map.exh"));
			EXDF_View itemView = new EXDF_View(exdIndexFile, "exd/map.exh", exhfFile);
			
			for (int i = 0; i < itemView.getTable().getRowCount(); i++)
			{
				String mapFolder =  "ui/map/" + itemView.getTable().getValueAt(i, 6);
				
				if (!((String)itemView.getTable().getValueAt(i, 6)).contains("/"))
					continue;
					
				String[] split = ((String)itemView.getTable().getValueAt(i, 6)).split("/");
				
				HashDatabase.addPathToDB( mapFolder + "/" + split[0] + split[1] + "_m.tex", "060000");
				HashDatabase.addPathToDB( mapFolder + "/" + split[0] + split[1] + "_s.tex", "060000");
				HashDatabase.addPathToDB( mapFolder + "/" + split[0] + split[1] + "m_m.tex", "060000");
				HashDatabase.addPathToDB( mapFolder + "/" + split[0] + split[1] + "m_s.tex", "060000");
				HashDatabase.addPathToDB( mapFolder + "/" + split[0] + split[1] + "d.tex", "060000");
				
				System.out.println("Added maps for: " + mapFolder + "/" + split[0] + split[1]);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
