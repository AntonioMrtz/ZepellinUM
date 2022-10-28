package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;

import persistencia.jpa.bean.CategoriaRestaurante;
import persistencia.jpa.bean.Incidencia;
import persistencia.jpa.bean.Plato;
import persistencia.jpa.bean.Restaurante;
import persistencia.jpa.bean.TipoUsuario;
import persistencia.jpa.bean.Usuario;
import persistencia.jpa.dao.CategoriaRestauranteDAO;
import persistencia.jpa.dao.IncidenciaDAO;
import persistencia.jpa.dao.PlatoDAO;
import persistencia.jpa.dao.RestauranteDAO;
import persistencia.jpa.dao.UsuarioDAO;
import zeppelinum.ServicioGestionPlataforma;

class OwnTest {

	private ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();

	@org.junit.jupiter.api.Test
	void checkCreateCategory() {
		Integer id = servicio.crearCategoria("categoriaPrueba");
		CategoriaRestaurante cr = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(id);
		assertNotNull(id);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(cr);
	}

	@org.junit.jupiter.api.Test
	void checkAddCategory() {

		Integer restaurante_id = servicio.registrarRestaurante("RE", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, new LinkedList<>());
		// get the restaurante in order to delete it.
		Restaurante restaurante = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);

		Integer cr_id = servicio.crearCategoria("categoriaPrueba");
		CategoriaRestaurante cr = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(cr_id);
		
		System.out.println(cr_id);
		System.out.println(restaurante_id);
		
		boolean added = servicio.addCategoria(restaurante_id, cr_id);
		
		boolean deletedR = RestauranteDAO.getRestauranteDAO().delete(restaurante);
		boolean deletedC = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(cr);
		assertTrue(added && deletedR && deletedC);
		// deleting the restaurante in order to get the old state of the db back


	}

	@org.junit.jupiter.api.Test
	void checkRegisterRestaurant() {

		LinkedList<Integer> categorias = new LinkedList<>();

		/* persist categories */
		Integer cat1_id = servicio.crearCategoria("cat1");
		Integer cat2_id = servicio.crearCategoria("cat2");
		categorias.add(cat1_id);
		categorias.add(cat2_id);

		Integer restaurante_id = servicio.registrarRestaurante("REst1", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, categorias);

		System.out.println("this is the id: " + restaurante_id);
		
		CategoriaRestaurante c1 = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(cat1_id);
		
		CategoriaRestaurante c2 = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(cat2_id);
		
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);

		assertEquals(2, r.getCategorias().size());
		
		// deleting the added entities
		RestauranteDAO.getRestauranteDAO().delete(r);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(c1);
		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(c2);

	}

	@org.junit.jupiter.api.Test
	void checkChangePlateAvailableness() {
		Integer cat1_id = servicio.crearCategoria("cat2");
		LinkedList<Integer> cats = new LinkedList<>();
		cats.add(cat1_id);
		Integer restaurante_id = servicio.registrarRestaurante("RestauranteR2", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, cats);
		Integer plato = servicio.nuevoPlato("plato1", "", 10, restaurante_id);
		
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		CategoriaRestaurante cr = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(cat1_id);
		Plato p = PlatoDAO.getPlatoDAO().findById(plato);
		assertTrue(servicio.cambiarDisponibilidadPlato(plato, false));
		

		CategoriaRestauranteDAO.getCategoriaRestauranteDAO().delete(cr);
		PlatoDAO.getPlatoDAO().delete(p);
		RestauranteDAO.getRestauranteDAO().delete(r);
	}

	@org.junit.jupiter.api.Test
	void checkCreateIncidencia() {

		LinkedList<Integer> cats = new LinkedList<>();
		cats.add(1);
		Integer restaurante_id = servicio.registrarRestaurante("rastaurante", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, cats);
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("Veratti", "Palotes", fechaNacimiento,
				"veratti@palotes.es", "12345", TipoUsuario.RESTAURANTE);
		Integer incidencia_id = servicio.crearIncidencia(fechaNacimiento, "description", fechaNacimiento, "",
				usuario_id, restaurante_id);
		Incidencia i = IncidenciaDAO.getIncidenciaDAO().findById(incidencia_id);
		
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario_id);
		
		assertNotNull(i);
		
		IncidenciaDAO.getIncidenciaDAO().delete(i);
		UsuarioDAO.getUsuarioDAO().delete(u);
		RestauranteDAO.getRestauranteDAO().delete(r);
	}

	@org.junit.jupiter.api.Test
	void checkIncidenciaLinked() {

		LinkedList<Integer> cats = new LinkedList<>();
		cats.add(1);
		Integer restaurante_id = servicio.registrarRestaurante("mano a mano", 1,"calle a", "30001",1 , "Murcia", 1.0,1.0, cats);
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario_id = servicio.registrarUsuario("someone else", "Palotes2", fechaNacimiento,
				"periquita@palotes.es", "12345", TipoUsuario.RESTAURANTE);
		Integer incidencia_id = servicio.crearIncidencia(fechaNacimiento, "description", fechaNacimiento, "",
				usuario_id, restaurante_id);
		Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario_id);
		Incidencia i = IncidenciaDAO.getIncidenciaDAO().findById(incidencia_id);
		Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante_id);
		assertEquals(i.getRestaurante().getId(), r.getId());
		
		IncidenciaDAO.getIncidenciaDAO().delete(i);
		UsuarioDAO.getUsuarioDAO().delete(u);
		RestauranteDAO.getRestauranteDAO().delete(r);

	}

}
