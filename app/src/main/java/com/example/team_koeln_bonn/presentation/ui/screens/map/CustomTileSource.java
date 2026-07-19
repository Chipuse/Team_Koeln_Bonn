package com.example.team_koeln_bonn.presentation.ui.screens.map;

import org.osmdroid.tileprovider.tilesource.TileSourcePolicy;
import org.osmdroid.tileprovider.tilesource.XYTileSource;

public class CustomTileSource extends XYTileSource {
    public CustomTileSource() {
        super("CustomTiles",
                0, 19, 256, ".png", new String[]{
                        "https://tile.openstreetmap.de/"}, "© OpenStreetMap contributors",
                new TileSourcePolicy(2,
                        TileSourcePolicy.FLAG_NO_BULK
                                | TileSourcePolicy.FLAG_NO_PREVENTIVE
                                | TileSourcePolicy.FLAG_USER_AGENT_MEANINGFUL
                                | TileSourcePolicy.FLAG_USER_AGENT_NORMALIZED
                ));
    }
}
