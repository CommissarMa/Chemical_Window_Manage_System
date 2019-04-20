package colorfind;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FindRedUsingHsv {
	
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}//º”‘ÿopencvø‚
	
	public static void main(String[] args) {
		Mat result=findColor(0,20);
		Imgcodecs.imwrite("D://redresult.png", result);
	}
	
	public static Mat findColor(int min,int max){
		 Mat source2 = Imgcodecs.imread("D://red.png");
		 Mat hsv_image=new Mat();
        Imgproc.GaussianBlur(source2, source2, new Size(3,3),0,0); 
        Imgproc.cvtColor(source2, hsv_image, Imgproc.COLOR_BGR2HSV);  
        Mat thresholded=new Mat();
        Core.inRange(hsv_image, new Scalar(min,90,90), new Scalar( max,255,255), thresholded);
	        return thresholded;
	}
}
