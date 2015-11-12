//package com.mdickson972.androidActivities;
//
//import com.mdickson972.programmingjava.R;
//
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.widget.Toast;
//import android.widget.VideoView;
//
//@SuppressWarnings("deprecation")
///**
// * This class was written to handle video lessons within the app but was not used in the end.
// * As an alternative the intent simply opens the lesson video in youtube.
// * @author mdickson972
// *
// */
//public class VideoLessonScreen extends ActionBarActivity {
//
//	private String lessonCalled;
//	private VideoView lessonVideoView;
//	private String url;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.video_lesson_screen);
//		lessonVideoView = (VideoView) findViewById(R.id.videoView);
//
//		// Retrieves the string value that was passed in with the intent
//		Bundle extras = getIntent().getExtras();
//		// Assigns the passed string to the lessonCalled variable
//		lessonCalled = extras.getString("lessonCalled");
//
//		// Calls the switchOnLesson method to populate the slides arraylist
//		switchOnLesson(lessonCalled);
//
//		// Assigns the appropriate slide to the image view
//		assignVideo(url);
//
//	}
//
//	/**
//	 * This method takes the string that identifies which lesson is being chosen
//	 * and then creates a switch statement to populate the url string variable
//	 * with the appropriate url for that lesson
//	 * 
//	 * @param lessonCalled
//	 */
//	public void switchOnLesson(String lessonCalled) {
//
//		switch (lessonCalled) {
//
//		case "'Assignment operators'":
//			url = "https://www.youtube.com/watch?v=M2x4iX2X0UY";
//			break;
//
//		case "'Increment/Decrement operators'":
//			url = "https://www.dropbox.com/s/yowfn3r33x261ys/loops_lesson2.mp4";
//			break;
//
//		case "'While loops'":
//			url = "https://www.youtube.com/watch?v=ZqRJC75OPZ4";
//			break;
//
//		case "'Do...while loops'":
//			url = "https://www.youtube.com/watch?v=3nm3YqOoqGk";
//			break;
//
//		case "'For loops'":
//			url = "https://www.youtube.com/watch?v=SZMx_vBh-ss";
//			break;
//
//		}
//
//	}
//
//	/**
//	 * Takes url for the current lesson and sets it as the source for the
//	 * videoView on the lessons page.
//	 * 
//	 * @param url
//	 */
//	public void assignVideo(String url) {
//		
//		
//		
//		
//	}
//
//	public void nextPress() {
//
//		finish();
//		Toast.makeText(getApplicationContext(),
//				"You have completed " + lessonCalled, Toast.LENGTH_SHORT)
//				.show();
//
//	}
//
//}
