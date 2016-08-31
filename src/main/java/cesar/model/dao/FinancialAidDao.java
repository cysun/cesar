package cesar.model.dao;

import java.util.List;

import cesar.model.FinancialAid;
import cesar.model.User;

public interface FinancialAidDao {

    public FinancialAid getFinancialAidById( Integer id );

    public List<FinancialAid> getFinancialAids( User user );

    public void saveFinancialAid( FinancialAid financialAid );

    public void deleteFinancialAid( FinancialAid financialAid );

}
