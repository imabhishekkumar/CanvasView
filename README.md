# CanvasView
Android library to create view that lets you draw on it.


## Add library to project:

### Step 1. Add the JitPack repository to your build file


Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
### Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.imabhishekkumar:CanvasView:1.0'
	}

## Implementation 

### In XML layout

	 <dev.abhishekkumar.canvasview.CanvasView
        	android:id="@+id/canvasView"
        	android:layout_width="match_parent"
        	android:layout_height="match_parent"/>

### Set background color, marker color, and stroke width

	val canvasView = findViewById<CanvasView>(R.id.canvasView)
        canvasView.setColorBackground(R.color.colorPrimary)
        canvasView.setColorMarker(R.color.colorAccent)
        canvasView.setStrokeWidth(12f)

### Get/Set Bitmap from/to view
	
 	canvasView.getBitmap()
	canvasView.setBitmap(bitmap)

### Clear view
	canvasView.clearView()
