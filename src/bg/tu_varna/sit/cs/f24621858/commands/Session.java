package bg.tu_varna.sit.cs.f24621858.commands;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Session {

    private final int id;

    //private List<NetpbmFormatImage> imageList = new ArrayList<NetpbmFormatImage>();

    public Session(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session session)) return false;
        return getId() == session.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
