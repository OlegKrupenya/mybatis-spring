package training.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import training.domain.Employee;

@Repository
public class EmployeeDao implements IEmployeeDao {

    @Autowired
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<Employee> getEmployees() {
        List<Employee> employees = null;
        employees = sqlSession
                .selectList("training.dao.EmployeeMapper.selectEmployeesWithCarsAndCompanies");
        return employees;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        Employee employee = null;
        Map<String, Object> params = new HashMap<>();
        params.put("id", employeeId);
        employee = sqlSession.selectOne(
                "training.dao.EmployeeMapper.selectEmployee", params);
        return employee;
    }

    @Override
    public Employee addEmployee(Employee employeeToAdd) {
        sqlSession.insert("training.dao.EmployeeMapper.insertEmployee",
                employeeToAdd);
        return employeeToAdd;
    }

    @Override
    public boolean updateEmployee(Employee employeeToUpdate) {
        try {
            sqlSession.update("training.dao.EmployeeMapper.updateEmployee",
                    employeeToUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteEmployee(Employee employeeToDelete) {
        try {
            sqlSession.update("training.dao.EmployeeMapper.deleteEmployee",
                    employeeToDelete);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
