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
                .selectList("com.testdev.dao.EmployeeMapper.selectEmployeesWithCarsAndCompanies");
        return employees;
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        Employee employee = null;
        Map<String, Object> params = new HashMap<>();
        params.put("id", employeeId);
        employee = sqlSession.selectOne(
                "com.testdev.dao.EmployeeMapper.selectEmployee", params);
        return employee;
    }

    @Override
    public Employee addEmployee(Employee employeeToAdd) {
        sqlSession.insert("com.testdev.dao.EmployeeMapper.insertEmployee",
                employeeToAdd);
        sqlSession.commit();
        return employeeToAdd;
    }

    @Override
    public boolean updateEmployee(Employee employeeToUpdate) {
        try {
            sqlSession.update("com.testdev.dao.EmployeeMapper.updateEmployee",
                    employeeToUpdate);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteEmployee(Employee employeeToDelete) {
        try {
            sqlSession.update("com.testdev.dao.EmployeeMapper.deleteEmployee",
                    employeeToDelete);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
