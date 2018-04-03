/*
 * Algorithm description: A simple Java project designed for copying a bunch of mp3 files to a different 
 * folder. During the transfer process, all files are scanned to decrease chances of duplicate files. Source path 
 * and destiny path are both configurable, depending of which folder you want to execute the transfer process.
 * 
 * Future projections: Implement a log file to detect and analyze existing errors for for future algorithm fixes.
 * 
 * First commit: 2018-04-01
 * 
 * Last commit: 2018-04-01
 * 
 * Author: Gustavo A. Lei
 */

package com.lei.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileMain 
{
	public static void main(String[] args)
	{
		
		long time1 = System.currentTimeMillis();
		
		//File source = new File("C:\\Users\\Gustavo\\Desktop\\Origem");
		//File destination = new File("C:\\Users\\Gustavo\\Desktop\\Destino");
		
		File source = new File("/home/gustavo-lei-voxus/Desktop/Origem");
		File destination = new File("/home/gustavo-lei-voxus/Desktop/Destino");
		
		int totalCopiedFiles = 0;
		
		try 
		{
			totalCopiedFiles = copyDir(source, destination);
		} 
		catch (IOException e) 
		{
			System.err.println("Error message: " + e.getMessage());
		}

		if(totalCopiedFiles >= 1)
		{
			long time2 = System.currentTimeMillis();
			System.out.println("Execution time: " + new SimpleDateFormat("mm:ss").format(new Date(time2 - time1)));
		}
	}
	
	public static int copyDir(File source, File destination) throws IOException 
	{
		
		int copiedFile = 0;
		if (source.isDirectory() && destination.getFreeSpace() >= 100000000) 
		{
			String arrayFiles[] = source.list();
			if (arrayFiles.length >= 1) 
			{
				for (String file : arrayFiles) 
				{
					if(file.endsWith(".doc"))
					{
						boolean status = verifyExists(destination, file);
						if(status == true)
						{
							System.out.println("File " + file.toString() + " is currently"
									+ " being transfered.\n");
							copyDir(new File(source, file),
										new File(destination, file));
							copiedFile++;
						}
					}
				}
				if(copiedFile >= 1)
				{
					System.out.println("Transfer process succesfully executed.\nTotal files transfered: " + (copiedFile));
				}
				else
				{
					System.out.println("All files already exists on destination folder.");
				}
			}
			else
			{
				System.out.println("Sorry, there's no files to be transfered.");
				System.exit(0);
			}
		}
		else
		{
			if(source.exists())
			{
				InputStream in = new FileInputStream(source);
		        OutputStream out = new FileOutputStream(destination);
		          
		        byte[] buffer = new byte[1024];
		        int length;
		        
		        while((length = in.read(buffer)) > 0)
		        {
		        	out.write(buffer, 0, length);
		        }
		        
		        source.deleteOnExit();
	        	in.close();
	        	out.close();
			}
			else
			{
				System.out.println("Destiny folder is not properly configured.");
			}
		}
		return copiedFile;
	}
	
	public static boolean verifyExists(File destinationPath, String file)
	{
		String arrayDestFiles[] = destinationPath.list();
		if(arrayDestFiles.length >= 1)
		{
			for(String destFile : arrayDestFiles)
			{
				if(destFile.equals(file))
				{
					return false;
				}
			}
		}
		return true;
	}
}
