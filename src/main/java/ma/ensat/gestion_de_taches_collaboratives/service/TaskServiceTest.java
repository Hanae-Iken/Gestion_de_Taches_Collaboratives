//package ma.ensat.gestion_de_taches_collaboratives.service;
//
//import ma.ensat.gestion_de_taches_collaboratives.entity.Task;
//import ma.ensat.gestion_de_taches_collaboratives.entity.User;
//import ma.ensat.gestion_de_taches_collaboratives.repository.TaskRepository;
//
//@ExtendWith(MockitoExtension.class)
//public class TaskServiceTest {
//
//    @Mock
//    private TaskRepository taskRepository;
//
//    @InjectMocks
//    private TaskService taskService;
//
//    private User utilisateur1;
//    private User utilisateur2;
//    private Task task;
//
//    @BeforeEach
//    public void setup() {
//        utilisateur1 = new User();
//        utilisateur1.setId(1L);
//        utilisateur1.setNom("Utilisateur 1");
//        utilisateur1.setEmail("user1@example.com");
//        utilisateur1.setRole(User.Role.USER);
//
//        utilisateur2 = new User();
