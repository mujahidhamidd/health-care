package com.healthcare;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
 DatabaseHelper databaseHelper;
    AlertDialog alertDialog;

  int pos= 0;
    private List<Reservation_Model> list ;
    private Context context;
    private String name_user;
    Reservation_Model res1 = null;
    public ReservationAdapter(Context context, List<Reservation_Model> List1 ) {
        this.context = context;
        this.list = List1;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reservation, parent, false));

    }
        // Lookup view for data population

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        final Holder Holder = (Holder) holder;



        final Reservation_Model res = list.get(position);

        Holder.time.setText(res.getTime());
        Holder.nameD.setText(res.getNameD());
        Holder.nameC.setText(res.getNameC());
        Holder.date.setText(res.getDate());



        Holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupMenu(Holder.itemView);
                pos = position;
                res1 = list.get(pos);






            }
        });


    }


    private void showPopupMenu(View view) {

        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.setting, popup.getMenu());
        popup.setOnMenuItemClickListener(new ReservationAdapter.MyMenuItemClickListener());
        popup.show();
    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {


                case R.id.edit:

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View alertLayout = inflater.inflate(R.layout.add_reservation, null);
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setView(alertLayout);
                    final EditText nameD=(EditText)alertLayout.findViewById(R.id.nameD);
                    final EditText nameC=(EditText)alertLayout.findViewById(R.id.nameC);
                    final EditText time=(EditText)alertLayout.findViewById(R.id.time);
                    final EditText date=(EditText)alertLayout.findViewById(R.id.date);
                    Button save=(Button) alertLayout.findViewById(R.id.save);
                    alertDialog = alert.create();
                    alertDialog.show();
                    nameC.setText(res1.getNameC());
                    nameD.setText(res1.getNameD());
                    date.setText(res1.getDate());
                    time.setText(res1.getTime());


                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseHelper = new DatabaseHelper(context);
                            Reservation_Model r = new Reservation_Model(res1.getId(),nameC.getText().toString(),nameD.getText().toString(),date.getText().toString(),time.getText().toString(),res1.getId_patient());
                            databaseHelper.updateRes(r);
                            notifyDataSetChanged();
                            list.clear();
                            list.addAll(databaseHelper.getAllReservatinList(res1.getId_patient()));
                            Toast.makeText(context,"Successfully edited",Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }
                    });
                    return true;

              
                case R.id.delete:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                    builder2.setMessage(" are you sure ?");
                    builder2.setCancelable(true)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    databaseHelper = new DatabaseHelper(context);
                                    databaseHelper.deleteRes(res1);
                                    notifyItemRemoved(pos);
                                    notifyDataSetChanged();
                                    list.clear();
                                    list.addAll(databaseHelper.getAllReservatinList(res1.getId_patient()));

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertdialog2 = builder2.create();
                    alertdialog2.show();
                    return true;
                default:
            }
            return false;
        }
    }











    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);

    }
    public class Holder extends RecyclerView.ViewHolder {
        TextView nameD,nameC,date,time ;

        public Holder(View convertView) {
            super(convertView);
              nameD  = convertView.findViewById(R.id.doctor);
             nameC = convertView.findViewById(R.id.clinic);
            date =   convertView.findViewById(R.id.date);
            time =  convertView.findViewById(R.id.time);
        }

    }
    }


