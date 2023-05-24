package model.enums;

public enum CarType {

    CAR_TYPE_UP("resources/images/CAR_TYPE_UP.png", 1),
    CAR_TYPE_RIGHT("resources/images/CAR_TYPE_RIGHT.png", 2),
    CAR_TYPE_DOWN("resources/images/CAR_TYPE_DOWN.png", 3),
    CAR_TYPE_LEFT("resources/images/CAR_TYPE_LEFT.png", 4),

    CAR_YEL_TYPE_UP("resources/images/CAR_YEL_TYPE_UP.png", 21),
    CAR_YEL_TYPE_RIGHT("resources/images/CAR_YEL_TYPE_RIGHT.png", 22),
    CAR_YEL_TYPE_DOWN("resources/images/CAR_YEL_TYPE_DOWN.png", 23),
    CAR_YEL_TYPE_LEFT("resources/images/CAR_YEL_TYPE_LEFT.png", 24),

    CAR_GRE_TYPE_UP("resources/images/CAR_GRE_TYPE_UP.png", 31),
    CAR_GRE_TYPE_RIGHT("resources/images/CAR_GRE_TYPE_RIGHT.png", 32),
    CAR_GRE_TYPE_DOWN("resources/images/CAR_GRE_TYPE_DOWN.png", 33),
    CAR_GRE_TYPE_LEFT("resources/images/CAR_GRE_TYPE_LEFT.png", 34),

    CAR_RED_TYPE_UP("resources/images/CAR_RED_TYPE_UP.png", 41),
    CAR_RED_TYPE_RIGHT("resources/images/CAR_RED_TYPE_RIGHT.png", 42),
    CAR_RED_TYPE_DOWN("resources/images/CAR_RED_TYPE_DOWN.png", 43),
    CAR_RED_TYPE_LEFT("resources/images/CAR_RED_TYPE_LEFT.png", 44);

    private String filePath;
    private int type;

    private CarType(String filePath, int type) {
        this.filePath = filePath;
        this.type = type;
    }

    public String toString() {
        return filePath;
    }

    public static String getMoveType(int type) {
        for (CarType carType : CarType.values()) {
            if (carType.type == type)
                return carType.toString();
        }
        return null;
    }

    public static String convertMoveType(int side){
        return switch (side) {
            case 5, 9 -> CAR_TYPE_UP.toString();
            case 6, 11 -> CAR_TYPE_RIGHT.toString();
            case 7, 12 -> CAR_TYPE_DOWN.toString();
            case 8, 10 -> CAR_TYPE_LEFT.toString();
            default -> null;
        };
    }
}
