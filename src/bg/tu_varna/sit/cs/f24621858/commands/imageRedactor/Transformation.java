package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

public interface Transformation {

    NetpbmFormatImage apply(NetpbmFormatImage image);

    String getName();
}
