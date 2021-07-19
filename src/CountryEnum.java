/**
 * @Author: zjh
 * @Date: 2021/6/1 10:40
 * @Version 1.0
 */
public enum CountryEnum {
    one(1,"齐",""),
    two(2,"楚","燕"),
    three(3,"燕","齐"),
    four(4,"赵","魏"),
    five(5,"韩","赵"),
    six(6,"魏","楚");

    private Integer code;
    private String value;
    private String next;

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getNext() {
        return next;
    }

    CountryEnum(Integer code, String value, String next) {
        this.code = code;
        this.value = value;
        this.next = next;
    }

    public static CountryEnum for_eachEnum(int index){

        CountryEnum[] values = CountryEnum.values();

        for(CountryEnum countryEnum:values){
            if(index == countryEnum.code){
                return countryEnum;
            }
        }
        return null;
    }

}
