package model.enums;

public enum CarType {

    CAR_TYPE_UP("resources/images/CAR_TYPE_UP.png", 1),
    CAR_TYPE_RIGHT("resources/images/CAR_TYPE_RIGHT.png", 2),
    CAR_TYPE_DOWN("resources/images/CAR_TYPE_DOWN.png", 3),
    CAR_TYPE_LEFT("resources/images/CAR_TYPE_LEFT.png", 4);

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
