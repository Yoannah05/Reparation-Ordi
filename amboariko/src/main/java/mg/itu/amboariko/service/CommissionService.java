package mg.itu.amboariko.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mg.itu.amboariko.model.Commission;
import mg.itu.amboariko.repository.CommissionRepository;

@Service
public class CommissionService {

    @Autowired
    private CommissionRepository commissionRepository;

    public Commission saveCommission(Commission commission) {
        return commissionRepository.save(commission);
    }

}