package com.aurora.config;

import com.aurora.impuestos.entidades.CodigoImpuestoRetencion;
import com.aurora.impuestos.repositorios.CodigoImpuestoRetencionRepositorio;
import com.aurora.inventario.entidades.Producto;
import com.aurora.inventario.entidades.UnidadMedida;
import com.aurora.inventario.repositorios.ProductoRepositorio;
import com.aurora.inventario.repositorios.UnidadMedidaRepositorio;
import com.aurora.pos.entidades.*;
import com.aurora.pos.repositorios.*;
import com.aurora.seguridad.entidades.Usuario;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Random;

@SpringComponent
public class DataGenerator implements HasLogger {

	private static final String[] FILLING = new String[] { "Strawberry", "Chocolate", "Blueberry", "Raspberry",
			"Vanilla" };
	private static final String[] TYPE = new String[] { "Cake", "Pastry", "Tart", "Muffin", "Biscuit", "Bread", "Bagel",
			"Bun", "Brownie", "Cookie", "Cracker", "Cheese Cake" };
	private static final String[] FIRST_NAME = new String[] { "Ori", "Amanda", "Octavia", "Laurel", "Lael", "Delilah",
			"Jason", "Skyler", "Arsenio", "Haley", "Lionel", "Sylvia", "Jessica", "Lester", "Ferdinand", "Elaine",
			"Griffin", "Kerry", "Dominique" };
	private static final String[] LAST_NAME = new String[] { "Carter", "Castro", "Rich", "Irwin", "Moore", "Hendricks",
			"Huber", "Patton", "Wilkinson", "Thornton", "Nunez", "Macias", "Gallegos", "Blevins", "Mejia", "Pickett",
			"Whitney", "Farmer", "Henry", "Chen", "Macias", "Rowland", "Pierce", "Cortez", "Noble", "Howard", "Nixon",
			"Mcbride", "Leblanc", "Russell", "Carver", "Benton", "Maldonado", "Lyons" };

	private final Random random = new Random(1L);

	//private final List<PickupLocation> pickupLocations = new ArrayList<>();
	//private final List<Product> products = new ArrayList<>();
	private Usuario baker;
	//private Usuario barista;

	@Bean
	public CommandLineRunner loadData(PasswordEncoder passwordEncoder,UserRepository userRepository) {
		return args -> {
			if (hasData(userRepository)) {
				getLogger().info("Using existing database");
				return;
			}

			getLogger().info("Generating demo data");
			getLogger().info("... generating users");

			createUsers(userRepository, passwordEncoder);
			getLogger().info("... generating products");
			getLogger().info("... generating impuestos");
			createImpuestos();
			getLogger().info("... generating productos");
			createProductos();
			getLogger().info("... generating productos");
			createEstablecimiento();
			getLogger().info("... generating establecimentos");
			createUnidadMedida();
			getLogger().info("... generating unidades de medida");
			createCodigoImpuestosRetencion();
			getLogger().info("... generating codigos impuestos");
			getLogger().info("Generated demo data");
		};
	}

	private boolean hasData(UserRepository userRepository) {
		return userRepository.count() != 0L;
	}



	private String getRandomPhone() {
		return "+1-555-" + String.format("%04d", random.nextInt(10000));
	}

	@Autowired
	private CodigoImpuestoRetencionRepositorio codigoImpuestoRetencionRepositorio;

	public void createCodigoImpuestosRetencion()
	{
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("2","9","Porcentaje IVA 10%",new BigDecimal("10")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("2","10","Porcentaje IVA 20%",new BigDecimal("20")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("2","1","Porcentaje IVA 30%",new BigDecimal("30")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("2","11","Porcentaje IVA 50%",new BigDecimal("40")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("2","2","Porcentaje IVA 70%",new BigDecimal("50")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("2","3","Porcentaje IVA 100%",new BigDecimal("60")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("2","7","Retención en cero 0%",new BigDecimal("70")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("2","8","No procede retención IVA 0%",new BigDecimal("80")));

		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("6","4580","PORCENTAJE ISD 5%",new BigDecimal("90")));

		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("6","303","Honorarios profesionales y demás pagos por servicios relacionados con el título profesional",new BigDecimal("10")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("6","304","Servicios predomina el intelecto no relacionados con el título profesional",new BigDecimal("10")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("6","304A","Comisiones y demás pagos por servicios predomina intelecto no relacionados con el título profesional",new BigDecimal("10")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("6","304B","Pagos a notarios y registradores de la propiedad y mercantil por sus actividades ejercidas como tales",new BigDecimal("10")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("6","304C","Pagos a deportistas, entrenadores, árbitros, miembros del cuerpo técnico por sus actividades ejercidas como\n" +
				"tales",new BigDecimal("10")));
		codigoImpuestoRetencionRepositorio.save( new CodigoImpuestoRetencion("6","304D","Pagos a artistas por sus actividades ejercidas como tales",new BigDecimal("10")));

		codigoImpuestoRetencionRepositorio.save(new CodigoImpuestoRetencion("1","304","Servicios predomina el intelecto no relacionados con el título profesional ",new BigDecimal("8")));
	}

	@Autowired
	private ImpuestoRepositorio impuestoRepositorio;

	@Autowired
	private ProductoRepositorio productoRepositorio;

	@Autowired
	private EstablecimientoRepositorio establecimientoRepositorio;

	private void createUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		Usuario user = new Usuario("admin@admin.com", "Administrador", passwordEncoder.encode("admin.123"), Rol.ADMIN);
		baker = userRepository.save(user);
	}

	private void createImpuestos()
	{
		Impuesto exentoIva = new Impuesto();
		exentoIva.setCodigo("2");
		exentoIva.setCodigoPorcentaje("4");
		exentoIva.setTarifa(BigDecimal.ZERO);
		exentoIva.setNombre("Exento Iva");
		impuestoRepositorio.save(exentoIva);

		Impuesto ivaNoImpuesto = new Impuesto();
		ivaNoImpuesto.setCodigo("2");
		ivaNoImpuesto.setCodigoPorcentaje("6");
		ivaNoImpuesto.setTarifa(BigDecimal.ZERO);
		ivaNoImpuesto.setNombre("No Objeto de Impuesto");
		impuestoRepositorio.save(ivaNoImpuesto);

		Impuesto iva14 = new Impuesto();
		iva14.setCodigo("2");
		iva14.setCodigoPorcentaje("2");
		iva14.setTarifa(new BigDecimal("14"));
		iva14.setNombre("IVA 14%");
		impuestoRepositorio.save(iva14);

		Impuesto iva12 = new Impuesto();
		iva12.setCodigo("2");
		iva12.setCodigoPorcentaje("2");
		iva12.setTarifa(new BigDecimal("12"));
		iva12.setNombre("IVA 12%");
		impuestoRepositorio.save(iva12);


		Impuesto iva0 = new Impuesto();
		iva0.setCodigo("2");
		iva0.setCodigoPorcentaje("0");
		iva0.setTarifa(BigDecimal.ZERO);
		iva0.setNombre("IVA 0%");
		impuestoRepositorio.save(iva0);

		Impuesto ice3620 = new Impuesto();
		ice3620.setNombre("ICE 3620");
		ice3620.setCodigo("3");
		ice3620.setCodigoPorcentaje("3620");
		ice3620.setTarifa(new BigDecimal("35"));
		ice3620.setDescripcion("Videojuegos");
		impuestoRepositorio.save(ice3620);

		Impuesto ice3610 = new Impuesto();
		ice3610.setNombre("ICE 3610");
		ice3610.setCodigo("3");
		ice3610.setCodigoPorcentaje("3610");
		ice3610.setTarifa(new BigDecimal("20"));
		ice3610.setDescripcion("Perfumes y aguas de tocador");
		impuestoRepositorio.save(ice3610);

		Impuesto ice3023 = new Impuesto();
		ice3023.setNombre("ICE 3023");
		ice3023.setCodigo("3");
		ice3023.setCodigoPorcentaje("3023");
		ice3023.setTarifa(new BigDecimal("150"));
		ice3023.setDescripcion("Productos del tabaco y sucedáneos del tabaco (abarcan los\n" +
				"productos preparados totalmente o en parte utilizando como\n" +
				"materia prima hojas de tabaco y destinados a ser fumados,\n" +
				"chupados, inhalados, mascados o utilizados como rapé).");
		impuestoRepositorio.save(ice3023);
	}

	private void createProductos()
	{

		Producto producto1 = new Producto();
		producto1.setCodigo("001");
		producto1.setNombre("Cuaderno 100 Hojas");
		producto1.setDescripcion("Cuaderno 100h");

		this.productoRepositorio.save(producto1);


		Producto producto2 = new Producto();
		producto2.setCodigo("002");
		producto2.setNombre("Cuaderno 200 Hojas");
		producto2.setDescripcion("Cuaderno 200h");

		this.productoRepositorio.save(producto2);
	}

	private void createEstablecimiento()
	{

		Establecimiento establecimiento1 = new Establecimiento();
		establecimiento1.setCodigo("001");

		establecimiento1.setNombreComercial("Establecimiento 1");

		PuntoEmision p001001 = new PuntoEmision();
		p001001.setCodigo("001");
		p001001.setDescripcion("Establecimiento 01");

		establecimiento1.getPuntos().add(p001001);
		establecimiento1.setDireccion("Bellavista Mz 35 Villa 10");
		p001001.setEstablecimiento(establecimiento1);
		establecimientoRepositorio.save(establecimiento1);
	}

	@Autowired
	private UnidadMedidaRepositorio unidadMedidaRepositorio;

	private void createUnidadMedida()
	{
		UnidadMedida unidad = new UnidadMedida();
		unidad.setCodigo("001");
		unidad.setNombre("Unidad");
		unidadMedidaRepositorio.save(unidad);

		UnidadMedida libra = new UnidadMedida();
		libra.setCodigo("002");
		libra.setNombre("Libra");
		unidadMedidaRepositorio.save(libra);

		UnidadMedida kilo = new UnidadMedida();
		kilo.setNombre("Kilogramo");
		kilo.setCodigo("003");
		unidadMedidaRepositorio.save(kilo);

	}
}
