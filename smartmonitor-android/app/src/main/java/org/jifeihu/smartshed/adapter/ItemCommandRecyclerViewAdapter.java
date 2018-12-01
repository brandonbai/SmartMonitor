package org.jifeihu.smartshed.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;

import org.jifeihu.smartshed.R;
import org.jifeihu.smartshed.bean.Command;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.interfaces.IConstants;
import org.jifeihu.smartshed.network.OkHttpManager;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ItemCommandRecyclerViewAdapter extends RecyclerView.Adapter<ItemCommandRecyclerViewAdapter.ViewHolder> {

    private OkHttpManager mOkHttpManager;
    private List<Command> commandList;

    public ItemCommandRecyclerViewAdapter() {
        mOkHttpManager = OkHttpManager.getOkHttpManager();
    }

    public void setCommands(List<Command> sensorList) {
        commandList = sensorList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_command, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Command command = commandList.get(position);
        holder.btn.setText(command.getName());
    }

    @Override
    public int getItemCount() {
        return commandList ==null?0: commandList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        ViewHolder(View view) {
            super(view);
            btn = (Button) view.findViewById(R.id.btn_command);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =  ViewHolder.this.getAdapterPosition();
                    sendCommand(commandList.get(position).getCommand(), v);
                }
            });
        }
    }

    private void sendCommand(String command,final View v) {
        RequestBody requestBody = new FormBody.Builder().add(IConstants.KEY_COMMAND, command).build();
        Request request = new Request.Builder().url(mOkHttpManager.getPath()+OkHttpManager.URL_CONTROL)
                .post(requestBody).addHeader(IConstants.KEY_TOKEN, mOkHttpManager.getToken()).build();
        mOkHttpManager.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(v, v.getContext().getString(R.string.request_failed), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                MyResponse myResp =  new Gson().fromJson(result, MyResponse.class);
                if(myResp==null || myResp.getMeta()==null) {
                    Snackbar.make(v, v.getContext().getString(R.string.request_failed), Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(!myResp.getMeta().isSuccess()) {
                    Snackbar.make(v, myResp.getMeta().getMessage(), Snackbar.LENGTH_SHORT).show();
                    return;
                }
                Snackbar.make(v, v.getContext().getString(R.string.send_success), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
