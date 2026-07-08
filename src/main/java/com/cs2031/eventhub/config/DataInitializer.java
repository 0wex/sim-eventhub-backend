package com.cs2031.eventhub.config;

import com.cs2031.eventhub.category.domain.Category;
import com.cs2031.eventhub.category.infrastructure.CategoryRepository;
import com.cs2031.eventhub.event.domain.Event;
import com.cs2031.eventhub.event.infrastructure.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepo;
    private final EventRepository eventRepo;

    public DataInitializer(CategoryRepository categoryRepo, EventRepository eventRepo) {
        this.categoryRepo = categoryRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public void run(String... args) {
        seed("Música", "Conciertos y recitales en espacios públicos", List.of(
                new String[]{"Concierto de la Orquesta Sinfónica Municipal", "2026-08-08", "Teatro Municipal", "Repertorio clásico con obras de Beethoven y compositores peruanos."},
                new String[]{"Festival de Rock Emergente", "2026-08-15", "Anfiteatro del Parque de la Exposición", "Seis bandas locales compiten por grabar su primer EP."},
                new String[]{"Noche de Jazz y Bolero", "2026-08-22", "Plaza de Armas", "Velada al aire libre con ensambles de jazz y trío de boleros."},
                new String[]{"Recital de Música Andina", "2026-08-29", "Casa de la Cultura", "Sonidos tradicionales con arpa, quena y charango."},
                new String[]{"Tributo a la Música Criolla", "2026-09-05", "Alameda Central", "Homenaje a los grandes compositores criollos con peña abierta."}));

        seed("Teatro", "Obras y funciones para toda la familia", List.of(
                new String[]{"La Casa de Bernarda Alba", "2026-08-09", "Teatro Municipal", "Clásico de García Lorca por la compañía municipal de teatro."},
                new String[]{"Función de Títeres: El Zorro y el Cuy", "2026-08-16", "Parque Central", "Adaptación de un cuento andino para los más pequeños."},
                new String[]{"Obra Colectiva: Voces del Barrio", "2026-08-23", "Casa de la Cultura", "Creación colectiva con vecinos actores sobre historias del distrito."},
                new String[]{"Improvisación Teatral Abierta", "2026-08-30", "Centro Cultural Juvenil", "Match de improvisación donde el público propone las escenas."},
                new String[]{"Microteatro: Historias de mi Ciudad", "2026-09-06", "Teatro Municipal", "Cinco obras de quince minutos en espacios no convencionales."}));

        seed("Danza", "Presentaciones de danza tradicional y contemporánea", List.of(
                new String[]{"Gala de Ballet Municipal", "2026-08-10", "Teatro Municipal", "Presentación anual del elenco municipal con fragmentos de El Lago de los Cisnes."},
                new String[]{"Concurso de Marinera", "2026-08-17", "Plaza de Armas", "Parejas de todas las edades compiten en la danza bandera del norte."},
                new String[]{"Festival de Danzas Folklóricas", "2026-08-24", "Estadio Municipal", "Delegaciones de todo el país presentan danzas típicas."},
                new String[]{"Danza Contemporánea: Cuerpos en Tránsito", "2026-08-31", "Casa de la Cultura", "Pieza contemporánea sobre la migración y la memoria."},
                new String[]{"Encuentro de Break Dance", "2026-09-07", "Skatepark Municipal", "Batallas 1 vs 1 y exhibiciones de crews invitadas."}));

        seed("Cine", "Proyecciones gratuitas al aire libre y en sala", List.of(
                new String[]{"Cine bajo las Estrellas: Clásicos Peruanos", "2026-08-11", "Parque de la Exposición", "Proyección al aire libre de clásicos del cine nacional."},
                new String[]{"Ciclo de Cine Documental", "2026-08-18", "Biblioteca Municipal", "Documentales sobre patrimonio y naturaleza con conversatorio."},
                new String[]{"Festival de Cortometrajes Locales", "2026-08-25", "Casa de la Cultura", "Competencia de cortos filmados en el distrito."},
                new String[]{"Cine Infantil: Tarde de Animación", "2026-09-01", "Centro Cultural Juvenil", "Función familiar de películas animadas con palomitas gratis."},
                new String[]{"Muestra de Cine Latinoamericano", "2026-09-08", "Teatro Municipal", "Selección de películas premiadas de la región."}));

        seed("Literatura", "Ferias del libro, recitales y clubes de lectura", List.of(
                new String[]{"Feria del Libro Municipal", "2026-08-12", "Plaza de Armas", "Editoriales independientes, firmas de autores y trueque de libros."},
                new String[]{"Recital de Poesía Joven", "2026-08-19", "Biblioteca Municipal", "Micrófono abierto para poetas menores de 25 años."},
                new String[]{"Club de Lectura: Narrativa Peruana", "2026-08-26", "Biblioteca Municipal", "Sesión dedicada a cuentos de Julio Ramón Ribeyro."},
                new String[]{"Cuentacuentos para Niños", "2026-09-02", "Parque Central", "Narración oral de cuentos con títeres y música en vivo."},
                new String[]{"Conversatorio con Escritores Locales", "2026-09-09", "Casa de la Cultura", "Charla sobre el oficio de escribir y publicación independiente."}));

        seed("Arte", "Exposiciones y muestras de artistas locales", List.of(
                new String[]{"Exposición: Miradas de mi Distrito", "2026-08-13", "Casa de la Cultura", "Pinturas y esculturas de artistas vecinos sobre la vida local."},
                new String[]{"Muestra de Fotografía Urbana", "2026-08-20", "Galería Municipal", "Cincuenta fotografías que retratan la ciudad y su gente."},
                new String[]{"Mural Colectivo Comunitario", "2026-08-27", "Alameda Central", "Pintado de mural abierto a todos los vecinos con artistas guía."},
                new String[]{"Salón de Arte Joven", "2026-09-03", "Galería Municipal", "Concurso anual para artistas de 15 a 29 años."},
                new String[]{"Exposición de Arte Popular y Artesanía", "2026-09-10", "Plaza de Armas", "Retablos, cerámica y textiles de maestros artesanos."}));

        seed("Gastronomía", "Ferias y festivales de cocina local", List.of(
                new String[]{"Feria Gastronómica: Sabores de mi Tierra", "2026-08-14", "Plaza de Armas", "Treinta puestos de comida regional y concurso de platos típicos."},
                new String[]{"Festival del Pan Artesanal", "2026-08-21", "Alameda Central", "Panaderos tradicionales hornean en vivo y ofrecen degustaciones."},
                new String[]{"Concurso de Cocina Regional", "2026-08-28", "Mercado Municipal", "Cocineros aficionados compiten con recetas de familia."},
                new String[]{"Ruta del Café y el Cacao", "2026-09-04", "Parque Central", "Catas guiadas con productores de café y chocolate peruano."},
                new String[]{"Festival de Postres Tradicionales", "2026-09-11", "Plaza de Armas", "Picarones, mazamorra y suspiro en versión clásica y de autor."}));

        seed("Talleres", "Talleres gratuitos abiertos a la comunidad", List.of(
                new String[]{"Taller de Guitarra para Principiantes", "2026-08-15", "Casa de la Cultura", "Cuatro sesiones prácticas; no se necesita guitarra propia."},
                new String[]{"Taller de Pintura al Aire Libre", "2026-08-22", "Parque Central", "Técnicas básicas de acuarela pintando el paisaje del parque."},
                new String[]{"Taller de Escritura Creativa", "2026-08-29", "Biblioteca Municipal", "Ejercicios de cuento corto con retroalimentación grupal."},
                new String[]{"Taller de Fotografía con Celular", "2026-09-05", "Centro Cultural Juvenil", "Composición, luz y edición usando solo el smartphone."},
                new String[]{"Taller de Teatro para Adolescentes", "2026-09-12", "Casa de la Cultura", "Juegos escénicos y montaje de una escena corta final."}));
    }

    private void seed(String name, String description, List<String[]> events) {
        Category category = categoryRepo.save(
                new Category(name, description, "https://via.placeholder.com/300"));
        events.forEach(e -> eventRepo.save(new Event(e[0], e[1], e[2], e[3], category)));
    }
}
