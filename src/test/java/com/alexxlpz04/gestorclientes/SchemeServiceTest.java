package com.alexxlpz04.gestorclientes;

import com.alexxlpz04.gestorclientes.dao.CompanyRepository;
import com.alexxlpz04.gestorclientes.dao.SchemaRepository;
import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.Schema;
import com.alexxlpz04.gestorclientes.services.SchemaService;
import com.alexxlpz04.gestorclientes.ui.SchemaForm;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SchemeServiceTest {

    @Mock
    private SchemaRepository schemaRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private SchemaService schemaService;
    @Autowired
    private HttpSession httpSession;

    @Nested
    @DisplayName("UNIT test for schemaList method")
    class SchemaListTests {

        @Test
        @DisplayName("this method list all the schemes for a company")
        void schemaList_CalledWithCorrectCompany_listSchemasForACompany() {
            // Arrange
            Model model = mock(Model.class);
            int companyId = 1;
            List<Schema> schemas = List.of(new Schema(), new Schema());
            when(schemaRepository.findByCompany(companyId)).thenReturn(schemas);
            // Act
            String result = schemaService.schemaList(model, companyId);
            // Assert
            assertEquals("schema/schema_list", result);
            verify(model, times(1)).addAttribute("schemas", schemas);
        }
    }

    @Nested
    @DisplayName("UNIT test for addSchema method")
    class addSchemaTests {

        @Test
        @DisplayName("this method prepares the model for adding a new schema")
        void addSchema_CalledWithCorrectly_redirectToAddSchemaPage() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            Company company = new Company();
            company.setId(33);
            when(session.getAttribute("company")).thenReturn(company);

            SchemaForm schemaForm = new SchemaForm();
            schemaForm.setCompanyId(company.getId());

            // Act
            String result = schemaService.addSchema(model, session);
            // Assert
            assertEquals("schema/add_schema", result);
            verify(model, times(1)).addAttribute("schemaForm", schemaForm);
        }
    }

    @Nested
    @DisplayName("UNIT test for guardarSchema method")
    class guardarSchemaTests {

        @Test
        @DisplayName("this method prepares the model for editing an existent schema")
        void guardarSchema_forEdit_saveTheNewSchema() {
            // Arrange
            Company company = new Company();
            company.setId(33);

            SchemaForm schemaForm = new SchemaForm();
            schemaForm.setId(1);
            schemaForm.setName("name");
            schemaForm.setType("empresa");
            schemaForm.setMandatory(true);
            schemaForm.setCompanyId(company.getId());

            // Act
            String result = schemaService.guardarSchema(schemaForm, httpSession);
            // Assert
            assertEquals("redirect:/schema/", result);
            verify(schemaRepository, times(1)).findById(schemaForm.getId());
            verify(schemaRepository, times(1)).save(any());
        }
        @Test
        @DisplayName("this method prepares the model for adding a new schema")
        void guardarSchema_forAdd_saveTheNewSchema() {
            // Arrange
            Company company = new Company();
            company.setId(33);

            SchemaForm schemaForm = new SchemaForm();
            schemaForm.setCompanyId(company.getId());

            // Act
            String result = schemaService.guardarSchema(schemaForm, httpSession);
            // Assert
            assertEquals("redirect:/schema/", result);
            verify(schemaRepository, never()).findById(schemaForm.getId());
            verify(schemaRepository, times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("UNIT test for editSchema method")
    class editSchemaTests {

        @Test
        @DisplayName("this method prepares the model for editing an existing schema")
        void editSchema_schemaidIsCorrect_editTheSchemaAndRedirectToList() {
            // Arrange
            Model model = mock(Model.class);
            Company company = new Company();
            company.setId(33);

            Schema schema = new Schema();
            schema.setId(1);
            schema.setName("name");
            schema.setType("empresa");
            schema.setMandatory(true);
            schema.setCompany(company);

            SchemaForm schemaForm = new SchemaForm();
            schemaForm.cargarSchema(schema);

            when(schemaRepository.findById(schema.getId())).thenReturn(Optional.of(schema));

            // Act
            String result = schemaService.editSchema(model, schema.getId());
            // Assert
            assertEquals("schema/add_schema", result);
            verify(model, times(1)).addAttribute("schemaForm", schemaForm);
        }

        @Test
        @DisplayName("this method prepares the model for editing a non existing schema")
        void editSchema_schemaidIsIncorrect_redirectToErrorPage() {
            // Arrange
            Model model = mock(Model.class);
            int schemaid = 1;
            when(schemaRepository.findById(schemaid)).thenReturn(Optional.empty());

            // Act
            String result = schemaService.editSchema(model, schemaid);
            // Assert
            assertEquals("error/error_page", result);
            verify(model, times(1)).addAttribute("error", "Schema not found");
        }
    }

    @Nested
    @DisplayName("UNIT test for removeSchema method")
    class removeSchemaTests {

        @Test
        @DisplayName("this method prepares the model for delete an existing schema")
        void removeSchema_schemaidIsCorrect_editTheSchemaAndRedirectToList() {
            // Arrange
            Model model = mock(Model.class);
            Company company = new Company();
            company.setId(33);

            Schema schema = new Schema();
            schema.setId(1);
            schema.setName("name");
            schema.setType("empresa");
            schema.setMandatory(true);
            schema.setCompany(company);

            when(schemaRepository.findById(schema.getId())).thenReturn(Optional.of(schema));

            // Act
            String result = schemaService.removeSchema(model, schema.getId());
            // Assert
            verify(schemaRepository, times(1)).delete(schema);
            assertEquals("redirect:/schema/", result);
        }

        @Test
        @DisplayName("this method prepares the model for deleting a non existing schema")
        void removeSchema_schemaidIsIncorrect_redirectToErrorPage() {
            // Arrange
            Model model = mock(Model.class);
            int schemaid = 1;
            when(schemaRepository.findById(schemaid)).thenReturn(Optional.empty());

            // Act
            String result = schemaService.removeSchema(model, schemaid);
            // Assert
            verify(model, times(1)).addAttribute("error", "Schema not found");
            assertEquals("error/error_page", result);
        }
    }
}
