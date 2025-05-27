package com.fct.backfct.domain.services.Habitaciones;

import com.fct.backfct.domain.converters.EstadosIncidenciaMapper;
import com.fct.backfct.domain.converters.HabitacionesMapper;
import com.fct.backfct.domain.converters.ImagenesIncidenciaMapper;
import com.fct.backfct.domain.converters.LogsLimpiezaMapper;
import com.fct.backfct.domain.dto.*;
import com.fct.backfct.domain.models.dao.*;
import com.fct.backfct.domain.models.entity.*;
import com.fct.backfct.domain.services.EmailService;
import com.fct.backfct.security.entity.Empleados;
import com.fct.backfct.security.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class HabitacionesServiceImpl implements IHabitacionesService {

    @Autowired
    private IHabitacionesDao habitacionesDao;

    @Autowired
    private HabitacionesMapper habitacionesMapper;

    @Autowired
    private IEstadosHabitacionDao estadosHabitacionDao;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private IIncidenciasDao incidenciasDao;

    @Autowired
    private IImagenesIncidenciaDao imagenesIncidenciaDao;

    @Autowired
    private IEstadosIncidenciaDao estadosIncidenciaDao;

    @Autowired
    private EstadosIncidenciaMapper estadosIncidenciaMapper;

    @Autowired
    private ImagenesIncidenciaMapper imagenesIncidenciaMapper;

    @Autowired
    private IEmpleadosDao empleadosDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ILogsLimpiezaDao logsLimpiezaDao;

    @Autowired
    private LogsLimpiezaMapper logsLimpiezaMapper;





    @Override
    public List<HabitacionesDTO> findAll() {
        return List.of();
    }

    @Override
    public List<HabitacionesDTO> findByEstado(String estado) {
        return List.of();
    }

    @Override
    public List<HabitacionesDTO> findAllDisponibles(LocalDateTime fechaEntrada, LocalDateTime fechaSalida, String tipo, Integer capacidad) {
        List<Habitaciones> habitaciones =  habitacionesDao.findHabitacionesDisponibles(fechaEntrada, fechaSalida, tipo, capacidad);
        return habitacionesMapper.toListDtos(habitaciones);
    }

    @Override
    public List<HabitacionesDTO> findAllLimpieza(Integer planta) {
        List<Habitaciones> habitaciones =  habitacionesDao.findHabitacionesLimpieza(planta);
        return habitacionesMapper.toListDtos(habitaciones);
    }

    @Override
    public List<HabitacionesDTO> limpiar( HttpServletRequest request, List<HabitacionesDTO> habitacionesDTO) {
        List<Habitaciones> habitaciones =  habitacionesMapper.toListEntities(habitacionesDTO);
        List<Habitaciones> habitacionesLimpias = new java.util.ArrayList<>(List.of());
        for(Habitaciones habitacion : habitaciones) {
            if(habitacion.getEstadoHabitacion().getIdEstado() == 3L){
                habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(1L).orElse(null));
                habitacion.setLimpiadoPor(jwtProvider.getUserFromToken(request));
                habitacion.setLimpiado(LocalDateTime.now());
                habitacionesDao.save(habitacion);
                habitacionesLimpias.add(habitacion);
                log.info("Habitacion " + habitacion.getIdHabitacion() + " limpia");
            }else{
                habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(4L).orElse(null));
                habitacion.setLimpiadoPor(jwtProvider.getUserFromToken(request));
                habitacion.setLimpiado(LocalDateTime.now());
                habitacionesDao.save(habitacion);
                habitacionesLimpias.add(habitacion);
                log.info("Habitacion " + habitacion.getIdHabitacion() + " limpia, ahora esta en mantenimiento");
            }
        }
        return habitacionesMapper.toListDtos(habitacionesLimpias);
    }

    @Override
    public LogsLimpiezaDTO saveLogsLimpieza(HttpServletRequest request,LogsLimpiezaDTO logsLimpiezaDTO) {

        Habitaciones habitacion = habitacionesDao.findById(logsLimpiezaDTO.getIdHabitacion()).orElse(null);
        habitacion.setLimpiezaDiaria(0);
        habitacionesDao.save(habitacion);

        LogsLimpieza logsLimpieza = logsLimpiezaMapper.toEntity(logsLimpiezaDTO);
        logsLimpieza.setLimpiadoPor(jwtProvider.getUserFromToken(request).getId());
        logsLimpieza = logsLimpiezaDao.save(logsLimpieza);
        return logsLimpiezaMapper.toDto(logsLimpieza);
    }

    @Scheduled(cron = "0 0 6,14 * * *") // A las 06:00 y 14:00 cada día
    public void marcarLimpiezaDiariaParaOcupadas() {
        log.info("Marcando limpieza diaria para habitaciones ocupadas");
        habitacionesDao.actualizarLimpiezaDiariaParaOcupadas();
        log.info("Limpieza diaria marcada para habitaciones ocupadas");
    }




    @Override
    public IncidenciasDTO crearIncidencia(HttpServletRequest request, Long idHabitacion, String observaciones, List<MultipartFile> imagenes) {
        // 1. Crear y guardar la incidencia
        Incidencias incidencia = new Incidencias();
        incidencia.setIdHabitacion(idHabitacion);
        incidencia.setIdEmpleadoReporta(jwtProvider.getUserFromToken(request).getId());
        incidencia.setDescripcion(observaciones);
        incidencia.setIdEstadoIncidencia(1L);
        incidencia = incidenciasDao.save(incidencia);

        Habitaciones habitacion = habitacionesDao.findById(idHabitacion).orElse(null);
        if(habitacion.getEstadoHabitacion().getIdEstado() == 3L){
            habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(5L).orElse(null));
            habitacionesDao.save(habitacion);
        }else{
            habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(4L).orElse(null));
            habitacionesDao.save(habitacion);
        }

        List<ImagenesIncidencia> imagenesIncidencia= new java.util.ArrayList<>(List.of());


        // 2. Guardar las imágenes relacionadas
        for (MultipartFile imagen : imagenes) {
            if (!imagen.isEmpty()) {
                String ruta = guardarImagen(imagen, idHabitacion); // Implementas esta función
                ImagenesIncidencia imagenEntidad = new ImagenesIncidencia();
                imagenEntidad.setIncidencia(incidencia);
                imagenEntidad.setRutaImagen(ruta);
                imagenesIncidencia.add(imagenesIncidenciaDao.save(imagenEntidad));
            }
        }

        IncidenciasDTO incidenciaDTO = new IncidenciasDTO();
        incidenciaDTO.setIdIncidencia(incidencia.getIdIncidencia());
        incidenciaDTO.setIdHabitacion(incidencia.getIdHabitacion());
        incidenciaDTO.setIdEmpleadoReporta(incidencia.getIdEmpleadoReporta());
        incidenciaDTO.setDescripcion(incidencia.getDescripcion());
        incidenciaDTO.setFechaReporte(incidencia.getFechaReporte());
        incidenciaDTO.setEstado(estadosIncidenciaMapper.toDto( estadosIncidenciaDao.findById(incidencia.getIdEstadoIncidencia()).orElse(null)));
        incidenciaDTO.setImagenes(imagenesIncidenciaMapper.toListDtos(imagenesIncidencia));

//         3. Notificar la incidencia con imajenes adjuntas por correo
//        try {
//            Context context = new Context();
//            context.setVariable("numeroHabitacion", habitacion.getNumeroHabitacion());
//            //fecha en formto dd/MM/yyyy
//            context.setVariable("fecha", incidencia.getFechaReporte().getDate()+"/"+incidencia.getFechaReporte().getMonth()+"/"+incidencia.getFechaReporte().getYear());
//            context.setVariable("reportadoPor", jwtProvider.getUserFromToken(request).getNombre()+" "+jwtProvider.getUserFromToken(request).getApellidos());
//            context.setVariable("observaciones", incidenciaDTO.getDescripcion());
//
//            Thread.sleep(20000);
//            this.notificarIncidencia(context, habitacion);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


        return incidenciaDTO;
    }


    private void notificarIncidencia(Context context, Habitaciones habitacion) throws MessagingException {
        try{
            emailService.enviarCorreoIncidencia(
                    "intrahotel.soporte@gmail.com",
                    "Incidencia en Habitacion " + habitacion.getNumeroHabitacion(),
                    "incidencia-email",
                    context,
                    habitacion.getIdHabitacion()
            );
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Scheduled(fixedRate = 150000) // Cada 10 minutos (600,000 ms)
    public void procesarIncidenciasPendientes() {
        List<Incidencias> pendientes = incidenciasDao.findIncidenciasPendienteNotificacion(1L); // estado 1 = pendiente

        log.info("Procesando " + pendientes.size() + " incidencias pendientes");

        for (Incidencias incidencia : pendientes) {
            try {
                // Cambiar estado a procesando
                incidencia.setIdEstadoIncidencia(2L);
                incidenciasDao.save(incidencia);

                log.info("Notificando incidencia " + incidencia.getIdIncidencia());

                Habitaciones habitacion = habitacionesDao.findById(incidencia.getIdHabitacion()).orElse(null);
                Empleados empleado = empleadosDao.findById(incidencia.getIdEmpleadoReporta()).orElse(null);

                Context context = new Context();
                context.setVariable("numeroHabitacion", habitacion.getNumeroHabitacion());
                context.setVariable("fecha", incidencia.getFechaReporte().getDay()+"/"+incidencia.getFechaReporte().getMonth()+"/"+incidencia.getFechaReporte().getYear());
                context.setVariable("reportadoPor", empleado.getNombre()+" "+empleado.getApellidos());
                context.setVariable("observaciones", incidencia.getDescripcion());

                String to = "intrahotel.soporte@gmail.com";
                String subject = "Incidencia en Habitacion " + habitacion.getNumeroHabitacion();
                String template = "incidencia-email";

                emailService.enviarCorreoIncidencia(to, subject, template, context, habitacion.getIdHabitacion());

                log.info("Incidencia " + incidencia.getIdIncidencia() + " notificada");

                // Cambiar estado a enviado
                incidencia.setIdEstadoIncidencia(3L);
                incidenciasDao.save(incidencia);

            } catch (Exception e) {
                // Si algo falla, lo dejamos en estado 2 para intentar después o marcar con otro estado de error si lo prefieres
                log.error("Error al notificar incidencia " + incidencia.getIdIncidencia(), e);
                e.printStackTrace();
            }
        }
    }


    public String guardarImagen(MultipartFile archivo, Long idHabitacion) {
        try {
            // Obtener la ruta del directorio del proyecto
            String rutaBaseProyecto = new File(".").getCanonicalPath(); // O getAbsolutePath() con ajustes
            String rutaBase = rutaBaseProyecto + File.separator + "uploads" + File.separator + "incidencias" + File.separator + idHabitacion;

            // Crear carpeta si no existe
            File dir = new File(rutaBase);
            if (!dir.exists()) dir.mkdirs();

            // Nombre del archivo
            String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path rutaCompleta = Paths.get(rutaBase, nombreArchivo);

            // Guardar archivo
            Files.copy(archivo.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

            // Retornar la ruta relativa o absoluta
            return rutaCompleta.toString();

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }




    @Override
    public List<Habitaciones> buscarPorFiltrosHabitacion(FiltroBusquedaDTO filtro) {
        return habitacionesDao.buscarPorFiltrosHabitacion(
                vacioANullInt(filtro.getCapacidad()),
                vacioANullString(filtro.getEstado()),
                vacioANullString(filtro.getNumeroHabitacion()),
                vacioANullString(filtro.getTipo()),
                vacioANullInt(filtro.getPlanta())
        );
    }



    @Override
    public List<Habitaciones> buscarPorFiltrosCliente(FiltroBusquedaDTO filtro) {
        return habitacionesDao.buscarPorFiltrosCliente(
                vacioANullString(filtro.getNombre()),
                vacioANullString(filtro.getDni()),
                vacioANullString(filtro.getApellidos()),
                vacioANullString(filtro.getTelefono())
        );
    }

    @Override
    public List<Habitaciones> buscarPorFiltrosReserva(FiltroBusquedaDTO filtro) {
        // Buscar habitaciones según filtros relacionados con reservas
        return habitacionesDao.buscarPorFiltrosReserva(
                vacioANullDate(filtro.getFechaEntrada()),
                vacioANullDate(filtro.getFechaSalida()),
                vacioANullDate(filtro.getFechaReserva()),
                vacioANullLong(filtro.getEstadoReserva())
        );
    }

    @Override
    public HabitacionesDTO findById(Long idHabitacion) {
        Habitaciones habitacion = habitacionesDao.findById(idHabitacion).orElse(null);
        return habitacionesMapper.toDto(habitacion);
    }

    private Long vacioANullLong(Long estadoReserva) {
        return (estadoReserva == null) ? null : estadoReserva;
    }

    private Date vacioANullDate(Date fecha) {
        return (fecha == null) ? null : fecha;
    }

    // Metodo para convertir "" a null para que el query ignore ese filtro
    private String vacioANullString(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s;
    }

    private Integer vacioANullInt(Integer capacidad) {
        return (capacidad == null) ? null : capacidad;
    }
}
