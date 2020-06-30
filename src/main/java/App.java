import java.util.ArrayList;
import java.util.List;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;

    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }

    public String bestCharge(List<String> inputs) {
        //write code here
        SalesPromotion salesPromotion = salesPromotionRepository.findAll().get(1);
        List<String> relatedItems = salesPromotion.getRelatedItems();
        List<Item> items = itemRepository.findAll();
        double priceA=halfPrice(inputs,relatedItems);
        double priceB=fullReduction(inputs);
        String a;
        if(priceA>=priceB&&priceB>=30){
            a="============= Order details =============\n" +
                    toStringInput(inputs)+
                    "-----------------------------------\n" +
                    "Promotion used:\n" +
                    "满30减6 yuan，saving"+(normalPrice(inputs)-fullReduction(inputs))+"yuan\n" +
                    "-----------------------------------\n" +
                    "Total:"+fullReduction(inputs)+"yuan\n" +
                    "===================================";
        }else if(priceA<priceB){
            a="============= Order details =============\n" +
                    toStringInput(inputs)+
                    "-----------------------------------\n" +
                    "Promotion used:\n" +
                    "Half price for certain dishes (Braised chicken，Cold noodles)，saving"+(normalPrice(inputs)-halfPrice(inputs,relatedItems))+"yuan\n" +
                    "-----------------------------------\n" +
                    "Total:"+halfPrice(inputs,relatedItems)+"yuan\n" +
                    "===================================";
        }else{
            a="============= Order details =============\n" +
                    toStringInput(inputs)+
                    "-----------------------------------\n" +
                    "Total:"+normalPrice(inputs)+"yuan\n" +
                    "===================================";
        }

        System.out.println(a);
        return a;
    }
    public double halfPrice(List<String> inputs,List<String> relatedItems){
        double totalPrice=0;
        for (String commodity : inputs) {
            String commodityId = commodity.split("x")[0];
            String commodityCount = commodity.split("x")[1];
            Item item=getItemById(commodityId,itemRepository.findAll());
            if (relatedItems.contains(commodityId)) {
                totalPrice+=item.getPrice()/2*Double.parseDouble(commodityCount);
            }else{
                totalPrice+=item.getPrice()*Double.parseDouble(commodityCount);
            }
        }
        return totalPrice;
    }
    public double fullReduction(List<String> inputs){
        double totalPrice=0;
        for (String commodity : inputs) {
            String commodityId = commodity.split("x")[0];
            String commodityCount = commodity.split("x")[1];
            Item item=getItemById(commodityId,itemRepository.findAll());
            totalPrice+=item.getPrice()*Double.parseDouble(commodityCount);
        }
        totalPrice=totalPrice-(totalPrice/30)*6;
        return totalPrice;
    }
    public Item getItemById(String id,List<Item> items){
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
    public String toStringInput(List<String> inputs){
        String input="";
        for (String commodity : inputs) {
            String commodityId = commodity.split("x")[0];
            String commodityCount = commodity.split("x")[1];
            Item item=getItemById(commodityId,itemRepository.findAll());
            input+=item.getName()+"x"+commodityCount+"="+item.getPrice()*Double.parseDouble(commodityCount)+"yuan\n";
        }
        return input;
    }
    public double normalPrice(List<String> inputs){
        double totalPrice=0;
        for (String commodity : inputs) {
            String commodityId = commodity.split("x")[0];
            String commodityCount = commodity.split("x")[1];
            Item item=getItemById(commodityId,itemRepository.findAll());
            totalPrice+=item.getPrice()*Double.parseDouble(commodityCount);
        }
        return totalPrice;
    }



}
