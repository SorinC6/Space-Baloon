package ro.itspace.sorin.pfff.gameimpl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import ro.itspace.sorin.pfff.R;
import ro.itspace.sorin.pfff.User;
import ro.itspace.sorin.pfff.UserTest;


public class HighscoreActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private String currentUser;

    private DatabaseReference rootRef;

    private RecyclerView userRecycleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_highscore );



        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser().getUid();
        rootRef= FirebaseDatabase.getInstance().getReference().child( "Users" );

        userRecycleList = findViewById( R.id.users_recycleview_id );
        userRecycleList.setLayoutManager( new LinearLayoutManager( this ) );

    }


    @Override
    protected void onStart() {
        super.onStart();

        Query query=rootRef.orderByChild( "userScore" ).limitToFirst( 15 );

        FirebaseRecyclerOptions<UserTest> options=new FirebaseRecyclerOptions.Builder<UserTest>().setQuery( query,UserTest.class )
                .build();

        FirebaseRecyclerAdapter<UserTest,UsersViewHolder> adapter=new FirebaseRecyclerAdapter<UserTest, UsersViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull UserTest model) {
                holder.userNamee.setText( model.getUserName() );
                holder.userScoree.setText(String.valueOf( model.getUserScore()  ) );
            }

//            @Override
//            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull User model) {
//                holder.userNamee.setText( model.getUserName() );
//                holder.userScoree.setText(String.valueOf( model.getUserScore()  ) );
//            }


            @Override
            public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view= LayoutInflater.from( parent.getContext()).inflate( R.layout.users_display_layout ,parent,false);
                return new UsersViewHolder( view );
            }

        };

        userRecycleList.setAdapter( adapter );
        adapter.startListening();

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView userNamee;
        TextView userScoree;

        public UsersViewHolder(View itemView) {
            super( itemView );

            userNamee=itemView.findViewById( R.id.username_name_id );
            userScoree=itemView.findViewById( R.id.username_score_id );

        }
    }
}
