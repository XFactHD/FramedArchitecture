package xfacthd.framedarchitecture.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedarchitecture.common.FAContent;
import xfacthd.framedblocks.api.model.FramedBlockModel;
import xfacthd.framedblocks.api.util.FramedProperties;
import xfacthd.framedblocks.api.util.client.BakedQuadTransformer;

import java.util.*;

public class FramedFlutedPillarSocketModel extends FramedBlockModel
{
    private static final List<BakedQuad> DUMMY_LIST = new ArrayList<>();

    private final int width;
    private final boolean top;

    public FramedFlutedPillarSocketModel(BlockState state, BakedModel baseModel, int width)
    {
        super(state, baseModel);
        this.width = width;
        this.top = state.getValue(FramedProperties.TOP);
    }

    @Override
    protected void transformQuad(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad)
    {
        if ((top && quad.getDirection() == Direction.DOWN) || (!top && quad.getDirection() == Direction.UP))
        {
            FramedFlutedPillarModel.createFlutedTopBottom(quadMap, quad, width);

            FramedPillarSocketModel.createSocketTopBottom(quadMap, quad, width);

            List<BakedQuad> quadList = new ArrayList<>();
            FramedFlutedPillarModel.createTopBottomFluteTabs(quadList, quad, width, true);
            quadList.forEach(q -> BakedQuadTransformer.setQuadPosInFacingDir(q, .5F));
            quadMap.get(null).addAll(quadList);
        }
        else if (quad.getDirection().getAxis() != Direction.Axis.Y)
        {
            List<BakedQuad> quadList = new ArrayList<>();
            FramedFlutedPillarModel.createFlutedSides(DUMMY_LIST, quadList, quad, width);
            quadList.removeIf(q -> !BakedQuadTransformer.createHorizontalSideQuad(q, !top, .5F));
            quadMap.get(null).addAll(quadList);

            FramedPillarSocketModel.createSocketSides(quadMap, quad, top, width);
        }
    }



    public static FramedFlutedPillarSocketModel small(BlockState state, BakedModel baseModel)
    {
        return new FramedFlutedPillarSocketModel(state, baseModel, 8);
    }

    public static FramedFlutedPillarSocketModel small(BakedModel baseModel)
    {
        return new FramedFlutedPillarSocketModel(FAContent.blockFramedSmallFlutedPillarSocket.get().defaultBlockState(), baseModel, 8);
    }

    public static FramedFlutedPillarSocketModel medium(BlockState state, BakedModel baseModel)
    {
        return new FramedFlutedPillarSocketModel(state, baseModel, 12);
    }

    public static FramedFlutedPillarSocketModel medium(BakedModel baseModel)
    {
        return new FramedFlutedPillarSocketModel(FAContent.blockFramedFlutedPillarSocket.get().defaultBlockState(), baseModel, 12);
    }
}
