package xfacthd.framedarchitecture.common.data;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum GratePosition implements StringRepresentable
{
    NEGATIVE,
    CENTER,
    POSITIVE;

    private final String name = toString().toLowerCase(Locale.ROOT);

    @Override
    public String getSerializedName() { return name; }
}
