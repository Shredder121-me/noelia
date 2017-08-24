package chat.amy.noelia.network.http;

import chat.amy.noelia.message.NoeliaMessage;
import chat.amy.noelia.network.NoeliaNetworker;
import com.google.common.util.concurrent.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Collection;

/**
 * Unidirectional HTTP communication networker. This class is implemented by
 * using {@link #sendMany(String, Collection)} to send a collection of
 * {@link NoeliaMessage}s to the <code>dest</code>ination. All responses from
 * the destination are ignored, as the destination shouldn't be returning
 * anything more than <code>null</code>, at most.
 *
 * @author amy
 * @since 8/24/17.
 */
@SuppressWarnings("unused")
public class HttpNetworker implements NoeliaNetworker {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @SuppressWarnings("TypeMayBeWeakened")
    private final OkHttpClient client = new OkHttpClient();
    @Getter
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    @Getter
    private final Service networkService = new HttpNetworkerService();
    
    @Override
    public NetworkState sendMany(final String dest, final Collection<NoeliaMessage> messages) {
        try {
            client.newCall(new Builder()
                    .url(dest)
                    .post(RequestBody.create(JSON, gson.toJson(messages)))
                    .build()).execute();
            // Ignore the response since this is supposed to be unidirectional
            return NetworkState.OK;
        } catch(final IOException e) {
            e.printStackTrace();
            return NetworkState.ERR;
        }
    }
}
