package kr.or.bigs.app.dto;

import lombok.Getter;

import java.util.function.Function;

@Getter
public enum ForecastCategory {
    POP("강수확률", "%"),
    PTY("강수형태", null, value -> switch (value) {
        case "0" -> "없음";
        case "1" -> "비";
        case "2" -> "비/눈";
        case "3" -> "눈";
        case "4" -> "소나기";
        default -> null;
    }),
    PCP("1시간 강수량", "mm", value -> {
        try {
            float pcpValue = Float.parseFloat(value);

            if (pcpValue < 1.0) {
                return "1.0mm 미만";
            } else if (pcpValue < 30.0) {
                return pcpValue + "mm";
            } else if (pcpValue < 50.0) {
                return "30.0~50.0mm";
            } else {
                return "50.0mm 이상";
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }),
    REH("습도", "%"),
    SNO("1시간 신적설", "cm", value -> {
        try {
            float snoValue = Float.parseFloat(value);

            if (snoValue < 1.0) {
                return "1.0cm 미만";
            } else if (snoValue < 5.0) {
                return snoValue + "cm";
            } else {
                return "5.0cm 이상";
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }),
    SKY("하늘상태", null, value -> switch (value) {
        case "1" -> "맑음";
        case "3" -> "구름많음";
        case "4" -> "흐림";
        default -> null;
    }),
    TMP("1시간 기온", "℃"),
    TMN("일 최저기온", "℃"),
    TMX("일 최고기운", "℃"),
    UUU("풍속(동서성분)", "m/s"),
    VVV("풍속(남북성분)", "m/s"),
    WAV("파고", "M"),
    VEC("풍향", "deg"),
    WSD("풍속", "m/s");

    private final String name;
    private final String unit;
    private final Function<String, String> function;

    ForecastCategory(String name) {
        this.name = name;
        this.unit = null;
        this.function = null;
    }

    ForecastCategory(String name, String unit) {
        this.name = name;
        this.unit = unit;
        this.function = null;
    }

    ForecastCategory(String name, String unit, Function<String, String> function) {
        this.name = name;
        this.unit = unit;
        this.function = function;
    }

    public String getValueDescription(String value) {
        if (function != null) {
            return function.apply(value);
        }

        return null;
    }
}