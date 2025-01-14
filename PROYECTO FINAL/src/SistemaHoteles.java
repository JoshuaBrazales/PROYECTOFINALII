import java.util.ArrayList;
import java.util.Scanner;

abstract class Categoria {
    protected String descripcion;
    protected double tipoIVA;

    public Categoria(String descripcion, double tipoIVA) {
        this.descripcion = descripcion;
        this.tipoIVA = tipoIVA;
    }

    public abstract String mostrarInfo();
}

class CategoriaEstrellas extends Categoria {
    private int estrellas;

    public CategoriaEstrellas(int estrellas, String descripcion, double tipoIVA) {
        super(descripcion, tipoIVA);
        this.estrellas = estrellas;
    }

    @Override
    public String mostrarInfo() {
        return "Categoría: " + estrellas + " estrellas, Descripción: " + descripcion + ", IVA: " + tipoIVA + "%";
    }
}

class Hotel {
    private String nombre;
    private String direccion;
    private String telefono;
    private int anioConstruccion;
    private Categoria categoria;
    private ArrayList<Habitacion> habitaciones;

    public Hotel(String nombre, String direccion, String telefono, int anioConstruccion, Categoria categoria) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.anioConstruccion = anioConstruccion;
        this.categoria = categoria;
        this.habitaciones = new ArrayList<>();
    }

    public void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    public void cambiarCategoria(Categoria nuevaCategoria) {
        this.categoria = nuevaCategoria;
    }

    public String mostrarInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Hotel: ").append(nombre).append("\n")
                .append("Dirección: ").append(direccion).append("\n")
                .append("Teléfono: ").append(telefono).append("\n")
                .append("Año de Construcción: ").append(anioConstruccion).append("\n")
                .append(categoria.mostrarInfo()).append("\n")
                .append("Total Habitaciones: ").append(habitaciones.size()).append("\n");

        for (Habitacion habitacion : habitaciones) {
            info.append(habitacion.mostrarInfo()).append("\n");
        }
        return info.toString();
    }

    public void editarHotel(Scanner scanner) {
        System.out.println("Editar datos del hotel:");
        System.out.print("Nuevo nombre: ");
        this.nombre = scanner.nextLine();
        System.out.print("Nueva dirección: ");
        this.direccion = scanner.nextLine();
        System.out.print("Nuevo teléfono: ");
        this.telefono = scanner.nextLine();
        System.out.print("Nuevo año de construcción: ");
        this.anioConstruccion = Integer.parseInt(scanner.nextLine());
    }

    public ArrayList<Habitacion> obtenerHabitaciones() {
        return habitaciones;
    }

    public int contarHabitacionesDisponibles(String tipo) {
        int contador = 0;
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo().equals(tipo)) {
                contador++;
            }
        }
        return contador;
    }
}

class Habitacion {
    private static int contador = 0;
    private String codigo;
    private String tipo;
    private int planta;

    public Habitacion(String tipo, int planta) {
        contador++;
        this.codigo = tipo.substring(0, 1).toUpperCase() + String.format("%02d%02d", planta, contador);
        this.tipo = tipo;
        this.planta = planta;
    }

    public String mostrarInfo() {
        return "Código: " + codigo + ", Tipo: " + tipo + ", Planta: " + planta;
    }

    public String getTipo() {
        return tipo;
    }
}

abstract class Reserva {
    protected String nombre;
    protected String direccion;
    protected String telefono;
    protected double precio;
    protected String fechaInicio;
    protected String fechaFin;

    public Reserva(String nombre, String direccion, String telefono, double precio, String fechaInicio, String fechaFin) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public abstract String mostrarReserva();
}

class ReservaParticular extends Reserva {
    public ReservaParticular(String nombre, String direccion, String telefono, double precio, String fechaInicio, String fechaFin) {
        super(nombre, direccion, telefono, precio, fechaInicio, fechaFin);
    }

    @Override
    public String mostrarReserva() {
        return "Reserva Particular:\nNombre: " + nombre + ", Dirección: " + direccion + ", Teléfono: " + telefono + "\n" +
                "Precio: " + precio + ", Inicio: " + fechaInicio + ", Fin: " + fechaFin;
    }
}

class ReservaAgencia extends Reserva {
    private String cliente;

    public ReservaAgencia(String nombre, String direccion, String telefono, double precio, String fechaInicio, String fechaFin, String cliente) {
        super(nombre, direccion, telefono, precio, fechaInicio, fechaFin);
        this.cliente = cliente;
    }

    @Override
    public String mostrarReserva() {
        return "Reserva Agencia:\nAgencia: " + nombre + ", Cliente: " + cliente + ", Dirección: " + direccion + ", Teléfono: " + telefono + "\n" +
                "Precio: " + precio + ", Inicio: " + fechaInicio + ", Fin: " + fechaFin;
    }
}

public class SistemaHoteles {
    private static ArrayList<Hotel> hoteles = new ArrayList<>();
    private static ArrayList<Reserva> reservas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;


        inicializarHotelesPredeterminados();

        do {
            System.out.println("\n--- Menú de Gestión de Hoteles ---");
            System.out.println("1. Agregar Hotel");
            System.out.println("2. Listar Hoteles (Dos opciones como predeterminado)");
            System.out.println("3. Editar Hotel");
            System.out.println("4. Agregar Habitación a un Hotel");
            System.out.println("5. Crear Reserva");
            System.out.println("6. Listar Reservas");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    agregarHotel(scanner);
                    break;
                case 2:
                    listarHoteles();
                    break;
                case 3:
                    editarHotel(scanner);
                    break;
                case 4:
                    agregarHabitacion(scanner);
                    break;
                case 5:
                    crearReserva(scanner);
                    break;
                case 6:
                    listarReservas();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 7);

        scanner.close();
    }

    private static void inicializarHotelesPredeterminados() {
        // Crear un par de hoteles predeterminados
        Hotel hotel1 = new Hotel("Hotel Playa", "Playa de Tonsupa", "123456789", 2000, new CategoriaEstrellas(5, "Hotel de lujo", 16.0));
        hotel1.agregarHabitacion(new Habitacion("Suite", 1));
        hotel1.agregarHabitacion(new Habitacion("Doble", 1));
        hotel1.agregarHabitacion(new Habitacion("Doble", 2));
        hoteles.add(hotel1);

        Hotel hotel2 = new Hotel("Hotel Montaña", "Pichincha", "987654321", 2010, new CategoriaEstrellas(4, "Hotel boutique", 16.0));
        hotel2.agregarHabitacion(new Habitacion("Individual", 1));
        hotel2.agregarHabitacion(new Habitacion("Doble", 2));
        hoteles.add(hotel2);
    }

    private static void agregarHotel(Scanner scanner) {
        System.out.println("\n--- Agregar Nuevo Hotel ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Año de Construcción (Ejemplo: 1998): ");
        int anioConstruccion = Integer.parseInt(scanner.nextLine());
        System.out.print("Descripción de Categoría (Ejemplo: Hotel de lujo, Hotel boutique): ");
        String descripcionCategoria = scanner.nextLine();
        System.out.print("IVA de Categoría (Ejemplo: 16.0): ");
        double tipoIVA = Double.parseDouble(scanner.nextLine());
        System.out.print("Número de Estrellas (Ejemplo: 3, 4, 5): ");
        int estrellas = Integer.parseInt(scanner.nextLine());

        Categoria categoria = new CategoriaEstrellas(estrellas, descripcionCategoria, tipoIVA);
        Hotel hotel = new Hotel(nombre, direccion, telefono, anioConstruccion, categoria);
        hoteles.add(hotel);

        System.out.println("Hotel agregado exitosamente.");
    }

    private static void listarHoteles() {
        System.out.println("\n--- Lista de Hoteles ---");
        if (hoteles.isEmpty()) {
            System.out.println("No hay hoteles registrados.");
        } else {
            for (int i = 0; i < hoteles.size(); i++) {
                System.out.println((i + 1) + ". " + hoteles.get(i).mostrarInfo());
            }
        }
    }

    private static void editarHotel(Scanner scanner) {
        listarHoteles();
        if (!hoteles.isEmpty()) {
            System.out.print("Seleccione el número del hotel a editar: ");
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice >= 0 && indice < hoteles.size()) {
                hoteles.get(indice).editarHotel(scanner);
                System.out.println("Hotel editado exitosamente.");
            } else {
                System.out.println("Número de hotel inválido.");
            }
        }
    }

    private static void agregarHabitacion(Scanner scanner) {
        listarHoteles();
        if (!hoteles.isEmpty()) {
            System.out.print("Seleccione el número del hotel al que desea agregar una habitación: ");
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice >= 0 && indice < hoteles.size()) {
                System.out.print("Tipo de Habitación (Ejemplo: Suite, Doble, Individual): ");
                String tipo = scanner.nextLine();
                System.out.print("Planta (Ejemplo: 1, 2, 3): ");
                int planta = Integer.parseInt(scanner.nextLine());

                Habitacion habitacion = new Habitacion(tipo, planta);
                hoteles.get(indice).agregarHabitacion(habitacion);
                System.out.println("Habitación agregada exitosamente.");
            } else {
                System.out.println("Número de hotel inválido.");
            }
        }
    }

    private static void crearReserva(Scanner scanner) {
        System.out.println("\n--- Crear Nueva Reserva ---");
        System.out.println("Selecciona un hotel para reservar:");
        System.out.println("1. " + hoteles.get(0).mostrarInfo());
        System.out.println("2. " + hoteles.get(1).mostrarInfo());
        System.out.println("3. Agregar un hotel nuevo");
        System.out.print("Seleccione el número del hotel o 3 para agregar uno nuevo: ");
        int opcionHotel = Integer.parseInt(scanner.nextLine());

        Hotel hotelSeleccionado = null;
        if (opcionHotel == 1 || opcionHotel == 2) {
            hotelSeleccionado = hoteles.get(opcionHotel - 1);
        } else if (opcionHotel == 3) {
            agregarHotel(scanner);
            return;
        } else {
            System.out.println("Opción inválida.");
            return;
        }


        System.out.print("Tipo de habitación a reservar (Ejemplo: Suite, Doble, Individual): ");
        String tipoHabitacion = scanner.nextLine();
        int habitacionesDisponibles = hotelSeleccionado.contarHabitacionesDisponibles(tipoHabitacion);

        if (habitacionesDisponibles > 0) {
            System.out.println("Hay " + habitacionesDisponibles + " habitaciones de tipo " + tipoHabitacion + " disponibles.");


            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Dirección: ");
            String direccion = scanner.nextLine();
            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();
            System.out.print("Precio (Ejemplo: 1500.50): ");
            double precio = Double.parseDouble(scanner.nextLine());
            System.out.print("Fecha de Inicio (Ejemplo: 2025-01-15): ");
            String fechaInicio = scanner.nextLine();
            System.out.print("Fecha de Fin (Ejemplo: 2025-01-20): ");
            String fechaFin = scanner.nextLine();

            System.out.println("Tipo de Reserva: 1. Particular, 2. Agencia");
            int tipoReserva = Integer.parseInt(scanner.nextLine());

            if (tipoReserva == 1) {
                Reserva reserva = new ReservaParticular(nombre, direccion, telefono, precio, fechaInicio, fechaFin);
                reservas.add(reserva);
                System.out.println("Reserva particular realizada con éxito.");
            } else if (tipoReserva == 2) {
                System.out.print("Nombre del Cliente para la agencia: ");
                String cliente = scanner.nextLine();
                Reserva reserva = new ReservaAgencia(nombre, direccion, telefono, precio, fechaInicio, fechaFin, cliente);
                reservas.add(reserva);
                System.out.println("Reserva de agencia realizada con éxito.");
            } else {
                System.out.println("Opción de reserva inválida.");
            }
        } else {
            System.out.println("No hay habitaciones de ese tipo disponibles.");
        }
    }

    private static void listarReservas() {
        System.out.println("\n--- Lista de Reservas ---");
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            for (Reserva reserva : reservas) {
                System.out.println(reserva.mostrarReserva());
            }
        }
    }
}
