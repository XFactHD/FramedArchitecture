package xfacthd.framedarchitecture.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedarchitecture.common.FAContent;
import xfacthd.framedblocks.api.model.FramedBlockModel;
import xfacthd.framedblocks.api.util.FramedProperties;
import xfacthd.framedblocks.api.util.client.BakedQuadTransformer;
import xfacthd.framedblocks.api.util.client.ModelUtils;

import java.util.*;

public class FramedPillarSocketModel extends FramedBlockModel
{
    private final int width;
    private final boolean top;

    public FramedPillarSocketModel(BlockState state, BakedModel baseModel, int width)
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
            FramedPillarModel.createPillarTopBottom(quadMap, quad, width);

            createSocketTopBottom(quadMap, quad, width);
        }
        else if (quad.getDirection().getAxis() != Direction.Axis.Y)
        {
            List<BakedQuad> quadList = new ArrayList<>();
            FramedPillarModel.createPillarSides(quadList, quad, width);
            quadList.removeIf(q -> !BakedQuadTransformer.createHorizontalSideQuad(q, !top, .5F));
            quadMap.get(null).addAll(quadList);

            createSocketSides(quadMap, quad, top, width);
        }
    }



    public static void createSocketTopBottom(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad, int width)
    {
        float offset = (((16F - width) / 2F) / 2F) / 16F;

        //Lower ring
        createOffsetTopBottomQuad(quadMap, quad, 0, 0, offset, 1, 4F/16F);
        createOffsetTopBottomQuad(quadMap, quad, 1F - offset, 0, 1, 1, 4F/16F);
        createOffsetTopBottomQuad(quadMap, quad, offset, 0, 1F - offset, offset, 4F/16F);
        createOffsetTopBottomQuad(quadMap, quad, offset, 1F - offset, 1F - offset, 1, 4F/16F);

        //Upper ring
        createOffsetTopBottomQuad(quadMap, quad, offset, offset, offset * 2F, 1F - offset, .5F);
        createOffsetTopBottomQuad(quadMap, quad, 1F - (offset * 2), offset, 1F - offset, 1F - offset, .5F);
        createOffsetTopBottomQuad(quadMap, quad, offset * 2, offset, 1F - (offset * 2F), offset * 2, .5F);
        createOffsetTopBottomQuad(quadMap, quad, offset * 2, 1F - (offset * 2), 1F - (offset * 2F), 1F - offset, .5F);
    }

    private static void createOffsetTopBottomQuad(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad, float minX, float minZ, float maxX, float maxZ, float offset)
    {
        BakedQuad copy = ModelUtils.duplicateQuad(quad);
        if (BakedQuadTransformer.createTopBottomQuad(copy, minX, minZ, maxX, maxZ))
        {
            BakedQuadTransformer.setQuadPosInFacingDir(copy, offset);
            quadMap.get(null).add(copy);
        }
    }

    public static void createSocketSides(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad, boolean top, int width)
    {
        float offset = ((16 - width) / 2F) / 2F;

        BakedQuad copy = ModelUtils.duplicateQuad(quad);
        if (BakedQuadTransformer.createHorizontalSideQuad(copy, top, 4F/16F))
        {
            quadMap.get(quad.getDirection()).add(copy);
        }

        copy = ModelUtils.duplicateQuad(quad);
        if (BakedQuadTransformer.createSideQuad(copy, offset / 16F, top ? .5F : 4F/16F, 1F - (offset / 16F), top ? 12F/16F : .5F))
        {
            BakedQuadTransformer.setQuadPosInFacingDir(copy, 1F - (offset / 16F));
            quadMap.get(null).add(copy);
        }
    }



    public static FramedPillarSocketModel small(BlockState state, BakedModel baseModel)
    {
        return new FramedPillarSocketModel(state, baseModel, 8);
    }

    public static FramedPillarSocketModel small(BakedModel baseModel)
    {
        return new FramedPillarSocketModel(FAContent.blockFramedSmallPillarSocket.get().defaultBlockState(), baseModel, 8);
    }

    public static FramedPillarSocketModel medium(BlockState state, BakedModel baseModel)
    {
        return new FramedPillarSocketModel(state, baseModel, 12);
    }

    public static FramedPillarSocketModel medium(BakedModel baseModel)
    {
        return new FramedPillarSocketModel(FAContent.blockFramedPillarSocket.get().defaultBlockState(), baseModel, 12);
    }
}
