package cesar.model.dao;

import java.util.List;

import cesar.model.FinancialAidType;

public interface FinancialAidTypeDao {

    public FinancialAidType getFinancialAidTypeById( Integer id );

    public List<FinancialAidType> getFinancialAidTypes();

}
