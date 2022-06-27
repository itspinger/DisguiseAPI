package net.pinger.disguise;

import net.pinger.disguise.response.Response;

import java.util.function.Consumer;

public interface SkinManager {

	void getFromImage(String imageUrl, Consumer<Response<Skin>> response);

}
