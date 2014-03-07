package cl.zeteginea.csrdlnaserver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class VideoList extends Activity {
	
	private Cursor videocursor;	
    private int video_column_index;
    ListView videolist;
    Context context;
    int count;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.video_list);
          init_phone_video_grid();
    }

    private void init_phone_video_grid() {
          System.gc();
          String[] proj = { MediaStore.Video.Media._ID,
        		  MediaStore.Video.Media.DATA,
        		  MediaStore.Video.Media.DISPLAY_NAME,
        		  MediaStore.Video.Media.DURATION,
        		  MediaStore.Video.Media.SIZE,
        		  MediaStore.Video.Media.ALBUM };
          Cursor managedQuery = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        		  proj, null, null, null);
		  videocursor = managedQuery;
          count = videocursor.getCount();
          System.out.println("[debug]" + "Count: " + count);
          
          videolist = (ListView) findViewById(R.id.PhoneVideoList);
          videolist.setAdapter(new VideoAdapter(getApplicationContext()));
          videolist.setOnItemClickListener(new OnItemClickListener() {
          	
        	  @Override
              public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    System.gc();
                    video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                    videocursor.moveToPosition(position);
                    String filename = videocursor.getString(video_column_index);
                    
                    // custom dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(VideoList.this);
                    builder.setMessage("You clicked: " + filename)
                            .setTitle("Video Information")
                            .setCancelable(false)
                            .setNeutralButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    
                    //Log.d("ListadoVideos", filename);
                    System.out.println("[debug]" + "Abriendo Archivo " + filename);
              }
        });
          
    }


    public class VideoAdapter extends BaseAdapter {
          private Context vContext;

          public VideoAdapter(Context c) {
                vContext = c;
          }

          public int getCount() {
                return count;
          }

          public Object getItem(int position) {
                return position;
          }

          public long getItemId(int position) {
                return position;
          }

          public View getView(int position, View convertView, ViewGroup parent) {

        	  System.gc();
        	  
        	  ViewHolder holder;
        	  LinearLayout ll;
        	  
        	  Context context = vContext.getApplicationContext();
        	          	  
        	  // First: We work with the TextView
        	  video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
        	  videocursor.moveToPosition(position);
        	  String filename = videocursor.getString(video_column_index);
        	  video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
        	  videocursor.moveToPosition(position);
        	  String duracion = videocursor.getString(video_column_index);
        	  video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
        	  videocursor.moveToPosition(position);
        	  String peso = videocursor.getString(video_column_index);
        	          	         	  
        	  video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM);
        	  videocursor.moveToPosition(position);
        	  String album = videocursor.getString(video_column_index);
        	  filename = filename + " (" + album +")";
        	  
        	  // Second: We work with the ImageView
        	  /**
        	  long id = videocursor.getLong(videocursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
        	  videocursor.moveToPosition(position);
        	  ContentResolver crThumb = getContentResolver();
        	  BitmapFactory.Options options = new BitmapFactory.Options();
        	  options.inSampleSize = 1;
        	  Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id, MediaStore.Video.Thumbnails.MINI_KIND, options);
        	  **/
        	  Bitmap curThumb = Bitmap.createBitmap(96, 96, Bitmap.Config.ARGB_8888);
        	  
        	  if (convertView == null) {

        		  holder = new ViewHolder(context);
        		  holder.setData(curThumb, filename, peso, duracion);
        		  
        		  ll = new LinearLayout(vContext.getApplicationContext());
        		  ll.setTag(holder);
        		  ll.addView(holder.thumb);                    
        		  ll.addView(holder.text);

        	  } else {
        		  ll = (LinearLayout) convertView;
        		  holder = (ViewHolder) ll.getTag();
        		  holder.setData(curThumb, filename, peso, duracion);
        	  }
        	  return ll;
          }
    }	  
    
    public class ViewHolder {
    	
    	TextView text;
  	  	ImageView thumb;
  	  	Context context;
  	  	Utiles myUtiles;
  	  	
  	  	public ViewHolder(Context context) {
  	  		
  	  		this.text = new TextView(context);
  	  		this.text.setTextSize(14);
  	  		this.text.setTextColor(Color.parseColor("#000000"));
  	  		this.text.setPadding(2, 4, 2, 4);
  	  		
  	  		this.thumb = new ImageView(context);
  	  		this.thumb.setPadding(2, 2, 2, 2);
  	  		this.thumb.setAdjustViewBounds(true);
  	  		this.thumb.setMaxWidth(120);
		  
  	  		this.myUtiles = new Utiles();
  	  	}
  	  	
  	  	public void setData(Bitmap curThumb, String filename, String peso, String duracion) {
  	  		
  	  		this.text.setText(generateText(filename, peso, duracion));
  	  		this.thumb.setImageBitmap(curThumb);
  	  		// System.out.println("[debug]" + filename);
  	  	}
  	  	
  	  	public String generateText(String filename, String peso, String duracion) {
  	  		
  	  		String texto = filename;
  	  		
  	  		if(texto.length() > 33) {
  	  			texto = texto.substring(0, 30) + "...";
  	  		}
  	  		
  	  		peso = myUtiles.formatSize(peso);
  	  		texto += "\nPeso: " + peso;

  	  		duracion = myUtiles.formatDuration(duracion);
  	  		texto += "\nDuracion: " + duracion;
  	  		
  	  		return texto;
  	  	}
    }
}
