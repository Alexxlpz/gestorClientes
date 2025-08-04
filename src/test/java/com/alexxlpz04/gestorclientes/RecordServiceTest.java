package com.alexxlpz04.gestorclientes;

import com.alexxlpz04.gestorclientes.dao.AtributeRepository;
import com.alexxlpz04.gestorclientes.dao.RecordRepository;
import com.alexxlpz04.gestorclientes.dao.SchemaRepository;
import com.alexxlpz04.gestorclientes.entities.Atribute;
import com.alexxlpz04.gestorclientes.entities.Company;
import com.alexxlpz04.gestorclientes.entities.Schema;
import com.alexxlpz04.gestorclientes.services.RecordService;
import com.alexxlpz04.gestorclientes.ui.RecordForm;
import com.alexxlpz04.gestorclientes.ui.SchemaAtributePair;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import com.alexxlpz04.gestorclientes.entities.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecordServiceTest {

    @Nested
    @DisplayName("UNIT TESTS for the method listNonCompletedRecords")
    public class listNonCompletedRecords {

        @Mock
        private RecordRepository recordRepository;

        @InjectMocks
        private RecordService recordService;

        @Test
        @DisplayName("Test for listNonCompletedRecords with valid company")
        public void listNonCompletedRecords_correctCompany_redirectToListRecordsAndLoadRecords() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            Company company = new Company();
            company.setId(1);

            List<Record> records = new ArrayList<>();

            records.add(new Record());
            records.add(new Record());
            records.add(new Record());

            when(recordRepository.findByCompanyAndCompletedFalse(company.getId())).thenReturn(records);
            when(session.getAttribute("company")).thenReturn(company);
            // Act
            String result = recordService.listNonCompletedRecords(model, session);
            // Assert
            assertEquals("record/list_records", result);
            verify(model).addAttribute("records", records);
        }

        @Test
        @DisplayName("Test for listNonCompletedRecords with invalid company")
        public void listNonCompletedRecords_noCompany_redirectToErrorPage() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);

            when(session.getAttribute("company")).thenReturn(null);
            // Act
            String result = recordService.listNonCompletedRecords(model, session);
            // Assert
            assertEquals("error", result);
            verify(model).addAttribute("error", "No company found in session");
        }
    }

    @Nested
    @DisplayName("UNIT TESTS for the method viewRecord")
    public class viewRecord {

        @Mock
        private RecordRepository recordRepository;

        @Mock
        private SchemaRepository schemaRepository;

        @Mock
        private AtributeRepository atributeRepository;

        @InjectMocks
        private RecordService recordService;

        @Test
        @DisplayName("Test for viewRecord with valid record and schemas")
        public void viewRecord_validRecordAndSchemas_redirectToWriteRecordAndLoadRecordForm() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            Integer recordId = 1;
            int companyId = 1;

            Record record = new Record();
            record.setId(recordId);

            List<Schema> schemas = new ArrayList<>();
            Schema schema1 = new Schema();
            schema1.setId(1);
            schemas.add(schema1);

            when(recordRepository.findById(recordId)).thenReturn(Optional.of(record));
            when(schemaRepository.findByCompany(companyId)).thenReturn(schemas);

            Atribute atribute = new Atribute();
            atribute.setRecord(record);
            atribute.setScheme(schema1);
            atribute.setValue("");

            when(atributeRepository.findByRecordIdAndSchemaId(schema1.getId(), record.getId())).thenReturn(atribute);

            RecordForm recordForm = new RecordForm();
            recordForm.setRecord(record);
            SchemaAtributePair pair = new SchemaAtributePair();
            pair.setSchema(schema1);
            pair.setAtribute(atribute);
            List<SchemaAtributePair> pairList = new ArrayList<>();
            pairList.add(pair);
            recordForm.setAtributes(pairList);

            // Act
            String result = recordService.viewRecord(model, session, recordId, companyId);

            // Assert
            assertEquals("record/write_record", result);
            verify(model).addAttribute("recordForm", recordForm);
        }

        @Test
        @DisplayName("Test for viewRecord with valid record and schemas with no atributes created")
        public void viewRecord_validRecordAndSchemasNoAtributesCreated_redirectToWriteRecordLoadRecordFormAndCreateAtributes() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            Integer recordId = 1;
            int companyId = 1;

            Record record = new Record();
            record.setId(recordId);

            List<Schema> schemas = new ArrayList<>();
            Schema schema1 = new Schema();
            schema1.setId(1);
            schemas.add(schema1);

            when(recordRepository.findById(recordId)).thenReturn(Optional.of(record));
            when(schemaRepository.findByCompany(companyId)).thenReturn(schemas);

            when(atributeRepository.findByRecordIdAndSchemaId(schema1.getId(), record.getId())).thenReturn(null);


            // Act
            String result = recordService.viewRecord(model, session, recordId, companyId);

            // Assert
            assertEquals("record/write_record", result);
            verify(model).addAttribute(eq("recordForm"), any(RecordForm.class));
        }

        @Test
        @DisplayName("Test for viewRecord with null record not found in repository")
        public void viewRecord_invalidRecord_redirectToRecordList() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            Integer recordId = 1;
            int companyId = 1;

            List<Schema> schemas = new ArrayList<>();
            Schema schema1 = new Schema();
            schema1.setId(1);
            schemas.add(schema1);

            when(schemaRepository.findByCompany(companyId)).thenReturn(schemas);
            when(recordRepository.findById(recordId)).thenReturn(Optional.empty());

            // Act
            String result = recordService.viewRecord(model, session, recordId, companyId);

            // Assert
            assertEquals("redirect:/record/", result);
        }

        @Test
        @DisplayName("Test for viewRecord with empty schemas")
        public void viewRecord_emptySchemas_redirectToRecordList() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            Integer recordId = 1;
            int companyId = 1;

            when(schemaRepository.findByCompany(companyId)).thenReturn(new ArrayList<>());

            // Act
            String result = recordService.viewRecord(model, session, recordId, companyId);

            // Assert
            assertEquals("redirect:/record/", result);
        }

        @Test
        @DisplayName("Test for viewRecord with recordId null")
        public void viewRecord_recordIdNull_redirectToError() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            Integer recordId = null;
            int companyId = 1;

            // Act
            String result = recordService.viewRecord(model, session, recordId, companyId);

            // Assert
            assertEquals("error", result);
        }

        @Test
        @DisplayName("Test for viewRecord with companyId null")
        public void viewRecord_companyIdNull_redirectToError() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            Integer recordId = 1;
            Integer companyId = null;

            // Act
            String result = recordService.viewRecord(model, session, recordId, companyId);

            // Assert
            assertEquals("error", result);
        }
    }

    @Nested
    @DisplayName("UNIT TESTS for the method viewRecordCompleted")
    public class viewRecordCompleted {

        @Mock
        private RecordRepository recordRepository;

        @Mock
        private SchemaRepository schemaRepository;

        @Mock
        private AtributeRepository atributeRepository;

        @InjectMocks
        private RecordService recordService;

        @Test
        @DisplayName("Test for viewRecordCompleted with valid record form and schemas")
        public void viewRecordCompleted_validRecordFormAndSchemas_redirectToWriteRecordAndLoadRecordForm() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            RecordForm recordForm = new RecordForm();
            Record record = new Record();
            record.setId(1);
            recordForm.setRecord(record);
            int companyId = 1;

            List<Schema> schemas = new ArrayList<>();
            Schema schema1 = new Schema();
            schema1.setId(1);
            schemas.add(schema1);

            when(schemaRepository.findByCompany(companyId)).thenReturn(schemas);
            when(recordRepository.findById(record.getId())).thenReturn(Optional.of(record));

            Atribute atribute = new Atribute();
            atribute.setRecord(record);
            atribute.setScheme(schema1);
            atribute.setValue("");

            when(atributeRepository.findByRecordIdAndSchemaId(schema1.getId(), record.getId())).thenReturn(atribute);

            SchemaAtributePair pair = new SchemaAtributePair();
            pair.setSchema(schema1);
            pair.setAtribute(atribute);
            List<SchemaAtributePair> pairList = new ArrayList<>();
            pairList.add(pair);
            recordForm.setAtributes(pairList);

            // Act
            String result = recordService.viewRecordCompleted(model, session, recordForm, companyId);

            // Assert
            verify(recordRepository).save(record);
            verify(atributeRepository).save(atribute);
            assertEquals("redirect:/record/", result);
        }

        @Test
        @DisplayName("Test for viewRecordCompleted with empty schemas")
        public void viewRecordCompleted_pairWithNoAtribute_redirectToRecordList() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            RecordForm recordForm = new RecordForm();
            Record record = new Record();
            record.setId(1);
            recordForm.setRecord(record);
            int companyId = 1;

            List<Schema> schemas = new ArrayList<>();
            Schema schema1 = new Schema();
            schema1.setId(1);
            schemas.add(schema1);

            when(schemaRepository.findByCompany(companyId)).thenReturn(schemas);
            when(recordRepository.findById(record.getId())).thenReturn(Optional.of(record));

            Atribute atribute = new Atribute();
            atribute.setRecord(record);
            atribute.setScheme(schema1);
            atribute.setValue("");

            when(atributeRepository.findByRecordIdAndSchemaId(schema1.getId(), record.getId())).thenReturn(atribute);

            SchemaAtributePair pair = new SchemaAtributePair();
            pair.setSchema(schema1);
            pair.setAtribute(null);
            List<SchemaAtributePair> pairList = new ArrayList<>();
            pairList.add(pair);
            recordForm.setAtributes(pairList);

            // Act
            String result = recordService.viewRecordCompleted(model, session, recordForm, companyId);

            // Assert
            verify(model).addAttribute("error", "Ha habido un error al guardar la ficha");
            assertEquals("error", result);
        }

        @Test
        @DisplayName("Test for viewRecordCompleted with no record in repository")
        public void viewRecordCompleted_noRecordInRepository_redirectToRecordList() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            RecordForm recordForm = new RecordForm();
            Record record = new Record();
            record.setId(1);
            recordForm.setRecord(record);
            int companyId = 1;

            List<Schema> schemas = new ArrayList<>();
            Schema schema1 = new Schema();
            schema1.setId(1);
            schemas.add(schema1);

            when(schemaRepository.findByCompany(companyId)).thenReturn(schemas);
            when(recordRepository.findById(record.getId())).thenReturn(Optional.empty());

            // Act
            String result = recordService.viewRecordCompleted(model, session, recordForm, companyId);

            // Assert
            assertEquals("redirect:/record/", result);
        }

        @Test
        @DisplayName("Test for viewRecordCompleted with no record in recordForm")
        public void viewRecordCompleted_nullRecordFormGetRecord_redirectToRecordList() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            RecordForm recordForm = new RecordForm();
            int companyId = 1;

            // Act
            String result = recordService.viewRecordCompleted(model, session, recordForm, companyId);

            // Assert
            verify(model).addAttribute("error", "Ha habido un error al intentar ver el registro");
            assertEquals("error", result);
        }

        @Test
        @DisplayName("Test for viewRecordCompleted with no id in redord in recordForm")
        public void viewRecordCompleted_noRecordFormGetRecordId_redirectToRecordList() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            RecordForm recordForm = new RecordForm();
            recordForm.setRecord(new Record());
            int companyId = 1;

            // Act
            String result = recordService.viewRecordCompleted(model, session, recordForm, companyId);

            // Assert
            verify(model).addAttribute("error", "Ha habido un error al intentar ver el registro");
            assertEquals("error", result);
        }

        @Test
        @DisplayName("Test for viewRecordCompleted with empty schemas")
        public void viewRecordCompleted_emptySchemas_redirectToRecordList() {
            // Arrange
            Model model = mock(Model.class);
            HttpSession session = mock(HttpSession.class);
            RecordForm recordForm = new RecordForm();
            Record record = new Record();
            record.setId(1);
            recordForm.setRecord(record);
            int companyId = 1;

            when(schemaRepository.findByCompany(companyId)).thenReturn(new ArrayList<>());

            // Act
            String result = recordService.viewRecordCompleted(model, session, recordForm, companyId);

            // Assert
            assertEquals("redirect:/record/", result);
        }
    }
}
