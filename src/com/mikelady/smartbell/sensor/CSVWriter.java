package com.mikelady.smartbell.sensor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

@SuppressLint("SimpleDateFormat")
public class CSVWriter {

/*	FileOutputStream gyroOut;
	FileOutputStream gravOut;
	FileOutputStream linAccOut;
	FileOutputStream rotOut;
	FileOutputStream gpsOut;
	FileOutputStream accOut;*/
	FileOutputStream quatOut;
	FileOutputStream eulerOut;
	FileOutputStream rotOut;
	FileOutputStream axisOut;
	FileOutputStream twoVecOut;
	FileOutputStream diffOut;
	FileOutputStream gyroOut;
	FileOutputStream normOut;
	FileOutputStream correctOut;
	FileOutputStream correctLinOut;
	FileOutputStream rawOut;

	public CSVWriter(Context ctx) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		
		try {
/*			gyroOut = ctx.openFileOutput(sdf.format(new Date()) + "_gyro.csv", Context.MODE_WORLD_WRITEABLE);
			gravOut = ctx.openFileOutput(sdf.format(new Date()) + "_grav.csv", Context.MODE_WORLD_WRITEABLE);
			linAccOut = ctx.openFileOutput(sdf.format(new Date()) + "_acc.csv", Context.MODE_WORLD_WRITEABLE);
			rotOut = ctx.openFileOutput(sdf.format(new Date()) + "_rot.csv", Context.MODE_WORLD_WRITEABLE);
			gpsOut = ctx.openFileOutput(sdf.format(new Date()) + "_gps.csv", Context.MODE_WORLD_WRITEABLE);
			accOut = ctx.openFileOutput(sdf.format(new Date()) + "_acc.csv", Context.MODE_WORLD_WRITEABLE);
*/
//			File sdCard = Environment.getExternalStorageDirectory();
//			File dir = new File (sdCard.getAbsolutePath() + "/barbell/"+new Date());
//			dir.mkdirs();
//			File file = new File(dir, "filename");

//			FileOutputStream f = new FileOutputStream(file);
/*			
			File quatFile = new File(dir,"quat.csv");
			try {
				quatFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			quatOut = new FileOutputStream(quatFile);
			eulerOut = new FileOutputStream(new File(dir, "euler.csv"));
			rotOut = new FileOutputStream(new File(dir,"rot.csv"));
			axisOut = new FileOutputStream(new File(dir, "axis.csv"));
			twoVecOut = new FileOutputStream(new File(dir,"twoVec.csv"));
			diffOut = new FileOutputStream(new File(dir, "diff.csv"));
			
			normOut = new FileOutputStream(new File(dir,"norm.csv"));
			correctOut = new FileOutputStream(new File(dir, "correct.csv"));
			correctLinOut = new FileOutputStream(new File(dir, "lin.csv"));
			rawOut = new FileOutputStream(new File(dir, "raw.csv"));
			*/
			
//			quatOut = ctx.openFileOutput(sdf.format(new Date()) + "_quat.csv", Context.MODE_WORLD_WRITEABLE);
/*			eulerOut = ctx.openFileOutput(sdf.format(new Date()) + "_euler.csv", Context.MODE_WORLD_WRITEABLE);
			rotOut = ctx.openFileOutput(sdf.format(new Date()) + "_rot.csv", Context.MODE_WORLD_WRITEABLE);
			axisOut = ctx.openFileOutput(sdf.format(new Date()) + "_axis.csv", Context.MODE_WORLD_WRITEABLE);
			twoVecOut = ctx.openFileOutput(sdf.format(new Date()) + "_twoVec.csv", Context.MODE_WORLD_WRITEABLE);
			diffOut = ctx.openFileOutput(sdf.format(new Date()) + "_diff.csv", Context.MODE_WORLD_WRITEABLE);
*/			
//			gyroOut = ctx.openFileOutput(sdf.format(new Date()) + "_gyro.csv", Context.MODE_WORLD_WRITEABLE);
			normOut = ctx.openFileOutput(sdf.format(new Date()) + "_norm.csv", Context.MODE_WORLD_WRITEABLE);
//			correctOut = ctx.openFileOutput(sdf.format(new Date()) + "_correct.csv", Context.MODE_WORLD_WRITEABLE);
//			correctLinOut = ctx.openFileOutput(sdf.format(new Date()) + "_lin.csv", Context.MODE_WORLD_WRITEABLE);
//			rawOut = ctx.openFileOutput(sdf.format(new Date()) + "_raw.csv", Context.MODE_WORLD_WRITEABLE);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
/*			gyroOut.close();
			gravOut.close();
			linAccOut.close();
			rotOut.close();
			gpsOut.close();
			accOut.close();*/
			quatOut.close();

			eulerOut.close();
			rotOut.close();
			axisOut.close();
			twoVecOut.close();
			diffOut.close();
			
			gyroOut.close();
			normOut.close();
			correctOut.close();
			correctLinOut.close();
			rawOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeQuat(double x, double y, double z) {
        long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + x + ", " + y + ", " + z + "\n";
        try {
            quatOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void writeQuat(float[] filtTaredOrientQuat) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + filtTaredOrientQuat[0] + ", " + filtTaredOrientQuat[1] + ", " + filtTaredOrientQuat[2] + filtTaredOrientQuat[3] + "\n";
        try {
            quatOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void writeEuler(float[] bytes) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + bytes[0] + ", " + bytes[1] + ", " + bytes[2] + "\n";
        try {
            eulerOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}	
	
	public void writeRot(float[] bytes) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + bytes[0] + ", " + bytes[1] + ", "+ bytes[2] +", " + bytes[3] +", " + bytes[4] +", " + bytes[5] +", " + bytes[6] +", " + bytes[7] +", " + bytes[8] +"\n";
        try {
            rotOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void writeAxis(float[] bytes) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + bytes[0] + ", " + bytes[1] + ", " + bytes[2] + ", " + bytes[3] + "\n";
        try {
            axisOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	
	public void writeTwoVec(float[] bytes) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + bytes[0] + ", " + bytes[1] + ", " + bytes[2] + ", " + bytes[3] +", " + bytes[4] +", " + bytes[5] + "\n";
        try {
            twoVecOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}	
	
	public void writeDiff(float[] bytes) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + bytes[0] + ", " + bytes[1] + ", " + bytes[2] + ", " + bytes[3] + "\n";
        try {
            diffOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void writeGryo(float[] normalizedComponent) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + normalizedComponent[0] + ", " + normalizedComponent[1] + ", " + normalizedComponent[2] +"\n";
        try {
            gyroOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void writeNorm(float[] normalizedComponent) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + normalizedComponent[0] + ", " + normalizedComponent[1] + ", " + normalizedComponent[2] +", " + normalizedComponent[3] + ", " + normalizedComponent[4] + ", " + normalizedComponent[5] +", " + normalizedComponent[6] + ", " + normalizedComponent[7] + ", " + normalizedComponent[8] + "\n";
        try {
            normOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void writeNormGyro(float[] normalizedComponent){
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + normalizedComponent[0] + ", " + normalizedComponent[1] + ", " + normalizedComponent[2] +"\n";
        try {
            normOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void writeNormAccel(float[] normalizedComponent){
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + normalizedComponent[0] + ", " + normalizedComponent[1] + ", " + normalizedComponent[2] +"\n";
        try {
            normOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void writeCorrect(float[] correctedComponent) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + correctedComponent[0] + ", " + correctedComponent[1] + ", " + correctedComponent[2] +", " + correctedComponent[3] + ", " + correctedComponent[4] + ", " + correctedComponent[5] +", " + correctedComponent[6] + ", " + correctedComponent[7] + ", " + correctedComponent[8] + "\n";
        try {
            correctOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void writeCorrectLin(float[] correctedLinComponent) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + correctedLinComponent[0] + ", " + correctedLinComponent[1] + ", " + correctedLinComponent[2] + "\n";
        try {
            correctLinOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void writeRaw(float[] rawComponent) {
		long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + rawComponent[0] + ", " + rawComponent[1] + ", " + rawComponent[2] + "\n";
        try {
            rawOut.write(row.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
/*	public void writeGyro(double x, double y, double z) {
		long unixTime = System.currentTimeMillis();
		String row = "" + unixTime + ", " + x + ", " + y + ", " + z + "\n";
		try {
			gyroOut.write(row.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeGrav(double x, double y, double z) {
		long unixTime = System.currentTimeMillis();
		String row = "" + unixTime + ", " + x + ", " + y + ", " + z + "\n";
		try {
			gravOut.write(row.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeLinAcc(double x, double y, double z) {
		long unixTime = System.currentTimeMillis();
		String row = "" + unixTime + ", " + x + ", " + y + ", " + z + "\n";
		try {
			linAccOut.write(row.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public void writeAcc(double x, double y, double z) {
        long unixTime = System.currentTimeMillis();
        String row = "" + unixTime + ", " + x + ", " + y + ", " + z + "\n";
        try {
            accOut.write(row.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	public void writeRot(double x, double y, double z) {
		long unixTime = System.currentTimeMillis();
		String row = "" + unixTime + ", " + x + ", " + y + ", " + z + "\n";
		try {
			rotOut.write(row.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeGPS(double speed, double bearing, double alt, long time) {
		long unixTime = System.currentTimeMillis();
		String row = "" + unixTime + ", " + time + ", " + speed + ", " + bearing + ", " + alt + "\n";
		try {
			gpsOut.write(row.getBytes());
		}
			catch (IOException e) {
			 e.printStackTrace();
		}
	}*/
	
}
