package com.sara.project.drawData.dto;

import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.dto.DcpResponseDto;
import com.sara.project.dcpSet.DcpSet;
import com.sara.project.ddc.Ddc;
import com.sara.project.drawData.DrawData;
import com.sara.project.drawData.Shadow;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DrawDataResponseDto {

    private Long id;
    private String type;
    private String version;
    private String originX;
    private String originY;
    private BigDecimal left;
    private BigDecimal top;
    private BigDecimal width;
    private BigDecimal height;
    private String fill;
    private String stroke;
    private Integer strokeWidth;
    private String strokeDashArray;
    private String strokeLineCap;
    private Integer strokeDashOffset;
    private String strokeLineJoin;
    private boolean strokeUniform;
    private Integer strokeMiterLimit;
    private BigDecimal scaleX;
    private BigDecimal scaleY;
    private Integer angle;
    private boolean flipX;
    private boolean flipY;
    private Integer opacity;
    private Shadow shadow;
    private boolean visible;
    private String backgroundColor;
    private String fillRule;
    private String paintFirst;
    private String globalCompositeOperation;
    private Integer skewX;
    private Integer skewY;
    private Long typeId;
    private String offMsg;
    private String notMsg;
    private String notImg;
    private String offImg;
    private boolean erasable;
    private Integer cropX;
    private Integer cropY;
    private String src;
    private Integer crossOrigin;
    private Long groupId;
    //private String filters;

    /*새로 추가*/
    private Integer rx;
    private Integer ry;
    private boolean splitByGrapheme;
    private Integer charSpacing;
    private String direction;
    private String fontFamily;
    private BigDecimal fontSize;
    private String fontStyle;
    private String fontWeight;
    private BigDecimal lineHeight;
    private boolean linethrough;
    private BigDecimal minWidth;
    private boolean overline;
    private String pathSide;
    private Integer pathStartOffset;
    private String text;
    private String textAlign;
    private String textBackgroundColor;
    private boolean underline;

    private String gatewayId;
    private String machineType;
    private String machineAddr;
    private String didoType;
    private String inOutNum;

    private String onImg;
    private String onMsg;
    private String state;


    private String offFill;
    private String notFill;
    private String onFill;
    private String offBackColor;
    private String onBackColor;
    private String notBackColor;

    public static DrawDataResponseDto of(
            DrawData drawData
    ) {
       // log.info("{}",drawData);
        return DrawDataResponseDto.builder()
                .id(drawData.getId())
                .type(drawData.getType())
                .version(drawData.getVersion())
                .originX(drawData.getOriginX())
                .originY(drawData.getOriginY())
                .left(drawData.getLeftSize())
                .top(drawData.getTop())
                .width(drawData.getWidth())
                .height(drawData.getHeight())
                .fill(drawData.getFill())
                .stroke(drawData.getStroke())
                .strokeWidth(drawData.getStrokeWidth())
                .strokeDashArray(drawData.getStrokeDashArray())
                .strokeLineCap(drawData.getStrokeLineCap())
                .strokeDashOffset(drawData.getStrokeDashOffset())
                .strokeLineJoin(drawData.getStrokeLineJoin())
                .strokeUniform(drawData.isStrokeUniform())
                .strokeMiterLimit(drawData.getStrokeMiterLimit())
                .scaleX(drawData.getScaleX())
                .scaleY(drawData.getScaleY())
                .angle(drawData.getAngle())
                .flipX(drawData.isFlipX())
                .flipY(drawData.isFlipY())
                .opacity(drawData.getOpacity())
                .shadow(drawData.getShadow())
                .visible(drawData.isVisible())
                .backgroundColor(drawData.getBackgroundColor())
                .fillRule(drawData.getFillRule())
                .paintFirst(drawData.getPaintFirst())
                .globalCompositeOperation(drawData.getGlobalCompositeOperation())
                .skewX(drawData.getSkewX())
                .skewY(drawData.getSkewY())
                .typeId(drawData.getTypeId())
                .offMsg(drawData.getOffMsg())
                .notMsg(drawData.getNotMsg())
                .notImg(drawData.getNotImg())
                .offImg(drawData.getOffImg())
                .gatewayId(drawData.getGatewayId())
                .machineType(drawData.getMachineType())
                .machineAddr(drawData.getMachineAddr())
                .didoType(drawData.getDidoType())
                .inOutNum(drawData.getInOutNum())
                .erasable(drawData.isErasable())
                .cropX(drawData.getCropX())
                .cropY(drawData.getCropY())
                .src(drawData.getSrc())
                .crossOrigin(drawData.getCrossOrigin())
                .groupId(drawData.getGroupId())
                //.filters(drawData.getFilters())
                .rx(drawData.getRx())
                .ry(drawData.getRy())
                .splitByGrapheme(drawData.isSplitByGrapheme())
                .charSpacing(drawData.getCharSpacing())
                .direction(drawData.getDirection())
                .fontFamily(drawData.getFontFamily())
                .fontSize(drawData.getFontSize())
                .fontStyle(drawData.getFontStyle())
                .fontWeight(drawData.getFontWeight())
                .lineHeight(drawData.getLineHeight())
                .linethrough(drawData.isLinethrough())
                .minWidth(drawData.getMinWidth())
                .overline(drawData.isOverline())
                .pathSide(drawData.getPathSide())
                .pathStartOffset(drawData.getPathStartOffset())
                .text(drawData.getText())
                .textAlign(drawData.getTextAlign())
                .textBackgroundColor(drawData.getTextBackgroundColor())
                .underline(drawData.isUnderline())
                .onBackColor(drawData.getBackgroundColor())
                .onFill(drawData.getFill())
                .offBackColor(drawData.getOffBackColor())
                .offFill(drawData.getOffFill())
                .notBackColor(drawData.getNotBackColor())
                .notFill(drawData.getNotFill())
                .build();
    }


    public static DrawDataResponseDto of(
            DrawData drawData,
            Ddc diDto,
            Ddc doDto,
            DcpSet dcpSet,
            Boolean type

    ) {
        String status = "2";

        if (diDto != null){
            if (diDto.getDiStatus() == null){
                status = null;
            }else{
                status = diDto.getDiStatus().toString();
            }

        }

        if (doDto != null){
            if (doDto.getDoStatus() == null){
                status = null;
            }else{
                status = doDto.getDoStatus().toString();
            }

        }

        if(dcpSet != null){
            status = dcpSet.getValue();
        }

        String src = "";
        String text = "";
        String fill = "";
        String backgroud = "";
        if (status == null){
            src = drawData.getNotImg();
            text = drawData.getNotMsg();
            backgroud = drawData.getNotBackColor();
            fill = drawData.getNotFill();
        }else if(status.equals("0")){

            src = drawData.getOffImg();
            text = drawData.getOffMsg();
            backgroud = drawData.getOffBackColor();
            fill = drawData.getOffFill();
        }else if(status.equals("1")){

            src = drawData.getSrc();
            text = drawData.getText();
            backgroud =drawData.getBackgroundColor();
            fill = drawData.getFill();
        }else{
            if(drawData.getDidoType() != null && drawData.getDidoType().equals("DCP")){
                text = dcpSet.getValue();
            }else{
                src = drawData.getSrc();
                text = drawData.getText();
                fill =  drawData.getFill();
                backgroud = drawData.getBackgroundColor();

            }


        }

        return DrawDataResponseDto.builder()
                .id(drawData.getId())
                .type(drawData.getType())
                .version(drawData.getVersion())
                .originX(drawData.getOriginX())
                .originY(drawData.getOriginY())
                .left(drawData.getLeftSize())
                .top(drawData.getTop())
                .width(drawData.getWidth())
                .height(drawData.getHeight())
                .fill(type ? fill : drawData.getFill() )
                .stroke(drawData.getStroke())
                .strokeWidth(drawData.getStrokeWidth())
                .strokeDashArray(drawData.getStrokeDashArray())
                .strokeLineCap(drawData.getStrokeLineCap())
                .strokeDashOffset(drawData.getStrokeDashOffset())
                .strokeLineJoin(drawData.getStrokeLineJoin())
                .strokeUniform(drawData.isStrokeUniform())
                .strokeMiterLimit(drawData.getStrokeMiterLimit())
                .scaleX(drawData.getScaleX())
                .scaleY(drawData.getScaleY())
                .angle(drawData.getAngle())
                .flipX(drawData.isFlipX())
                .flipY(drawData.isFlipY())
                .opacity(drawData.getOpacity())
                .shadow(drawData.getShadow())
                .visible(drawData.isVisible())
                .backgroundColor(type ? backgroud : drawData.getBackgroundColor())
                .fillRule(drawData.getFillRule())
                .paintFirst(drawData.getPaintFirst())
                .globalCompositeOperation(drawData.getGlobalCompositeOperation())
                .skewX(drawData.getSkewX())
                .skewY(drawData.getSkewY())
                .typeId(drawData.getTypeId())
                .offMsg(drawData.getOffMsg())
                .notMsg(drawData.getNotMsg())
                .notImg(drawData.getNotImg())
                .offImg(drawData.getOffImg())
                .gatewayId(drawData.getGatewayId())
                .machineType(drawData.getMachineType())
                .machineAddr(drawData.getMachineAddr())
                .didoType(drawData.getDidoType())
                .inOutNum(drawData.getInOutNum())
                .erasable(drawData.isErasable())
                .cropX(drawData.getCropX())
                .cropY(drawData.getCropY())
                .src(type ? src : drawData.getSrc())
                .crossOrigin(drawData.getCrossOrigin())
                .groupId(drawData.getGroupId())
                //.filters(drawData.getFilters())
                .rx(drawData.getRx())
                .ry(drawData.getRy())
                .splitByGrapheme(drawData.isSplitByGrapheme())
                .charSpacing(drawData.getCharSpacing())
                .direction(drawData.getDirection())
                .fontFamily(drawData.getFontFamily())
                .fontSize(drawData.getFontSize())
                .fontStyle(drawData.getFontStyle())
                .fontWeight(drawData.getFontWeight())
                .lineHeight(drawData.getLineHeight())
                .linethrough(drawData.isLinethrough())
                .minWidth(drawData.getMinWidth())
                .overline(drawData.isOverline())
                .pathSide(drawData.getPathSide())
                .pathStartOffset(drawData.getPathStartOffset())
                .text( type ? text : drawData.getText())
                .textAlign(drawData.getTextAlign())
                .textBackgroundColor(drawData.getTextBackgroundColor())
                .underline(drawData.isUnderline())
                .state(status)
                .onMsg(drawData.getText())
                .onImg(drawData.getSrc())
                .onBackColor(drawData.getBackgroundColor())
                .onFill(drawData.getFill())
                .offBackColor(drawData.getOffBackColor())
                .offFill(drawData.getOffFill())
                .notBackColor(drawData.getNotBackColor())
                .notFill(drawData.getNotFill())
                .build();
    }
}
