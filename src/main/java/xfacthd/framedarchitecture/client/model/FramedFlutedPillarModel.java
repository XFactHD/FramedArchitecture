package xfacthd.framedarchitecture.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedarchitecture.common.FAContent;
import xfacthd.framedblocks.api.model.FramedBlockModel;
import xfacthd.framedblocks.api.util.client.BakedQuadTransformer;
import xfacthd.framedblocks.api.util.client.ModelUtils;

import java.util.List;
import java.util.Map;

public class FramedFlutedPillarModel extends FramedBlockModel
{
    private static final float PIXEL = 1F/16F;
    private final int width;

    private FramedFlutedPillarModel(BlockState state, BakedModel baseModel, int width)
    {
        super(state, baseModel);
        this.width = width;
    }

    @Override
    protected void transformQuad(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad)
    {
        if (quad.getDirection().getAxis() == Direction.Axis.Y)
        {
            createFlutedTopBottom(quadMap, quad, width);
        }
        else
        {
            createFlutedSides(quadMap.get(quad.getDirection()), quadMap.get(null), quad, width);
        }
    }



    public static void createFlutedTopBottom(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad, int width)
    {
        float offset = ((16F - width + 2F) / 2F) / 16F;

        BakedQuad copy = ModelUtils.duplicateQuad(quad);
        if (BakedQuadTransformer.createTopBottomQuad(copy, offset, offset, 1F - offset, 1F - offset))
        {
            quadMap.get(quad.getDirection()).add(copy);
        }

        createTopBottomFluteTabs(quadMap.get(quad.getDirection()), quad, width, false);
    }

    public static void createTopBottomFluteTabs(List<BakedQuad> quadList, BakedQuad quad, int width, boolean onSocket)
    {
        float offset = ((16F - width + 2F) / 2F) / 16F;

        for (int i = 0; i < width - 2; i++)
        {
            float minOff = offset + (i * PIXEL);
            BakedQuad copy = ModelUtils.duplicateQuad(quad);

            if ((i % 2 == 0) == onSocket)
            {
                float minOffOne = onSocket && i == 0 ? (minOff - PIXEL) : minOff;
                if (BakedQuadTransformer.createTopBottomQuad(copy, offset - PIXEL, minOffOne, offset, minOff + PIXEL))
                {
                    quadList.add(copy);
                }

                copy = ModelUtils.duplicateQuad(quad);
                if (BakedQuadTransformer.createTopBottomQuad(copy, minOffOne, 1F - offset, minOff + PIXEL, 1F - offset + PIXEL))
                {
                    quadList.add(copy);
                }
            }
            else
            {
                float maxOff = onSocket && i == (width - 3) ? (minOff + (PIXEL * 2)) : (minOff + PIXEL);
                if (BakedQuadTransformer.createTopBottomQuad(copy, 1F - offset, minOff, 1F - offset + PIXEL, maxOff))
                {
                    quadList.add(copy);
                }

                copy = ModelUtils.duplicateQuad(quad);
                if (BakedQuadTransformer.createTopBottomQuad(copy, minOff, offset - PIXEL, maxOff, offset))
                {
                    quadList.add(copy);
                }
            }
        }
    }

    public static void createFlutedSides(List<BakedQuad> faceQuads, List<BakedQuad> nullQuads, BakedQuad quad, int width)
    {
        float offset = ((16F - width + 2F) / 2F) / 16F;
        for (int i = 0; i < width - 2; i++)
        {
            //Flute faces
            float rightCut = offset + PIXEL + (i * PIXEL);
            float leftCut = (i == 0) ? (1F - offset + PIXEL) : (1F - rightCut + PIXEL);

            BakedQuad copy = ModelUtils.duplicateQuad(quad);
            if (BakedQuadTransformer.createVerticalSideQuad(copy, quad.getDirection().getClockWise(), leftCut) &&
                BakedQuadTransformer.createVerticalSideQuad(copy, quad.getDirection().getCounterClockWise(), rightCut)
            )
            {
                BakedQuadTransformer.setQuadPosInFacingDir(copy, i % 2 != 0 ? (1F - offset + PIXEL) : (1F - offset));
                if (width == 16 && i % 2 != 0)
                {
                    faceQuads.add(copy);
                }
                else
                {
                    nullQuads.add(copy);
                }
            }

            //Flute sides
            if (i > 0 && i < width - 3 && i % 2 == 0)
            {
                copy = ModelUtils.duplicateQuad(quad);
                if (BakedQuadTransformer.createVerticalSideQuad(copy, quad.getDirection().getClockWise(), 1F - offset + PIXEL) &&
                    BakedQuadTransformer.createVerticalSideQuad(copy, quad.getDirection().getCounterClockWise(), offset)
                )
                {
                    float qOff = 1F - offset - (i * PIXEL);
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, qOff);
                    nullQuads.add(copy);
                }
            }

            if (i % 2 != 0)
            {
                copy = ModelUtils.duplicateQuad(quad);
                if (BakedQuadTransformer.createVerticalSideQuad(copy, quad.getDirection().getClockWise(), offset) &&
                    BakedQuadTransformer.createVerticalSideQuad(copy, quad.getDirection().getCounterClockWise(), 1F - offset + PIXEL)
                )
                {
                    float qOff = 1F - offset - (i * PIXEL);
                    BakedQuadTransformer.setQuadPosInFacingDir(copy, qOff);
                    nullQuads.add(copy);
                }
            }
        }
    }



    public static FramedFlutedPillarModel small(BlockState state, BakedModel baseModel)
    {
        return new FramedFlutedPillarModel(state, baseModel, 8);
    }

    public static FramedFlutedPillarModel small(BakedModel baseModel)
    {
        return new FramedFlutedPillarModel(FAContent.blockFramedSmallFlutedPillar.get().defaultBlockState(), baseModel, 8);
    }

    public static FramedFlutedPillarModel medium(BlockState state, BakedModel baseModel)
    {
        return new FramedFlutedPillarModel(state, baseModel, 12);
    }

    public static FramedFlutedPillarModel medium(BakedModel baseModel)
    {
        return new FramedFlutedPillarModel(FAContent.blockFramedFlutedPillar.get().defaultBlockState(), baseModel, 12);
    }

    public static FramedFlutedPillarModel large(BlockState state, BakedModel baseModel)
    {
        return new FramedFlutedPillarModel(state, baseModel, 16);
    }

    public static FramedFlutedPillarModel large(BakedModel baseModel)
    {
        return new FramedFlutedPillarModel(FAContent.blockFramedLargeFlutedPillar.get().defaultBlockState(), baseModel, 16);
    }
}
