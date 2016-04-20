package it.cnvcrew.sonar;

import com.squareup.okhttp.Response;

/**
 * Created by Alessandro on 19/04/2016.
 */
public interface ResponseListener {

    void onApiResponseReceived(Response response);

}
