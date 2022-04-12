import db_layer.dao.CarsDAO;
import formatter.CarFormatter;
import formatter.CarShopFormatter;
import formatter.OwnersFormatter;
import formatter.ShopFormatter;
import lombok.Getter;

@Getter
public class FormatterFacade {
    private final CarFormatter carFormatter;
    private final CarShopFormatter carShopFormatter;
    private final OwnersFormatter ownersFormatter;
    private final ShopFormatter shopFormatter;

    public FormatterFacade() {
        this.carFormatter = new CarFormatter();
        this.carShopFormatter = new CarShopFormatter();
        this.ownersFormatter = new OwnersFormatter();
        this.shopFormatter = new ShopFormatter();
    }
}
