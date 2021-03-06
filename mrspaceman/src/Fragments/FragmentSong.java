package Fragments;



import java.io.File;
import java.util.ArrayList;

import com.labs.adapters.SongGridAdapter;
import com.labs.mrspaceman.R;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class FragmentSong extends Fragment {

	Button btn;
	OnSoundSelected listener;
	ArrayList<String> arrFiles;
	private static final String ARG_PARAM1 = "param1";
	String song_directory = "";
	GridView grid;
	public interface OnSoundSelected{
		public void OnSoundSelected(String sound_url); 
	}

	public void setListener(OnSoundSelected listener) {
		this.listener = listener;
	}

	public static FragmentSong newInstance(String param1) {
		FragmentSong fragment = new FragmentSong();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_song, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getArguments() != null) {
			song_directory = getArguments().getString(ARG_PARAM1);
        }
		init();
	}

	private void init() {
		arrFiles = new ArrayList<String>();
		SongGridAdapter adapter = new SongGridAdapter();
		grid = (GridView) getView().findViewById(R.id.gridView);
		final String path = Environment.getExternalStorageDirectory().toString()+"/"+"spaceman"+"/"+song_directory+"/";
		File f = new File(path);    
		File file[] = f.listFiles();
		for (int i=0; i < file.length; i++)
		{
			arrFiles.add(file[i].getName());
		}
		adapter.setData(arrFiles, getActivity());
		grid.setAdapter(adapter);

		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {


				if (listener != null) {
					listener.OnSoundSelected(path+arrFiles.get(position));
				}

			}


		});



	}

}
