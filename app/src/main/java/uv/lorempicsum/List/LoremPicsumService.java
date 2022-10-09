package uv.lorempicsum.List;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LoremPicsumService {

    @GET ("v2/list")
    Call<List<ImageResult>> getImages();

}

/*
    ELABORADO POR:
    - Sixtega Escribano Miguel Angel
    - Tapia Vazquez Alan Eduardo @TaponTV
    - Medina Zarate Alan Emmanuel
    Github:
        @Mignatech
        @TaponTV
        @Makarotk
    Copyright 2022
 */