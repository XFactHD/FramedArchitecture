package xfacthd.framedarchitecture.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.model.FramedBlockModel;
import xfacthd.framedblocks.api.util.client.BakedQuadTransformer;
import xfacthd.framedblocks.api.util.client.ModelUtils;

import java.util.List;
import java.util.Map;

public class FramedPillarModel extends FramedBlockModel
{
    public FramedPillarModel(BlockState state, BakedModel baseModel) { super(state, baseModel); }

    @Override
    protected void transformQuad(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad)
    {
        if (quad.getDirection().getAxis() == Direction.Axis.Y)
        {
            createPillarTopBottom(quadMap, quad, 12);
        }
        else
        {
            createPillarSides(quadMap.get(null), quad, 12);
        }
    }



    public static void createPillarTopBottom(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad, int width)
    {
        BakedQuad copy = ModelUtils.duplicateQuad(quad);
        float offset = ((16F - width) / 2F) / 16F;

        if (BakedQuadTransformer.createTopBottomQuad(copy, offset, offset, 1F - offset, 1F - offset))
        {
            quadMap.get(quad.getDirection()).add(copy);
        }
    }

    public static void createPillarSides(List<BakedQuad> quadList, BakedQuad quad, int width)
    {
        BakedQuad copy = ModelUtils.duplicateQuad(quad);
        float cut = 1F - (((16F - width) / 2F) / 16F);

        if (BakedQuadTransformer.createVerticalSideQuad(copy, quad.getDirection().getClockWise(), cut) &&
            BakedQuadTransformer.createVerticalSideQuad(copy, quad.getDirection().getCounterClockWise(), cut)
        )
        {
            BakedQuadTransformer.setQuadPosInFacingDir(copy, cut);
            quadList.add(copy);
        }
    }
}