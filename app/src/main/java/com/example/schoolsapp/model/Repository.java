package com.example.schoolsapp.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.example.schoolsapp.rest.callback.OnDBResponse;
import com.example.schoolsapp.rest.callback.OnSchoolsResponse;
import com.example.schoolsapp.rest.callback.OnTeachersResponse;
import com.example.schoolsapp.rest.client.SchoolClient;
import com.example.schoolsapp.rest.client.TeacherClient;
import com.example.schoolsapp.rest.pojo.DBResponse;
import com.example.schoolsapp.rest.pojo.School;
import com.example.schoolsapp.rest.pojo.Teacher;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private Context context;

    private Retrofit retrofit;
    private final static String REST_URL="https://informatica.ieszaidinvergeles.org:9039/PSP/colegiosApp/public/api/";
    private SchoolClient schoolClient;
    private TeacherClient teacherClient;

    public final static String[] STORAGE_PERMISSION = { Manifest.permission.READ_EXTERNAL_STORAGE };
    public final static int STORAGE_PERMISSION_CODE = 1;

    //NO HAY MutableLiveData s !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    private School currentSchool;
    private Teacher currentTeacher;




    public Repository(Context context){
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(REST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        schoolClient = retrofit.create(SchoolClient.class);
        teacherClient = retrofit.create(TeacherClient.class);
    }


    public School getCurrentSchool() {
        return currentSchool;
    }

    public void setCurrentSchool(School currentSchool) {
        this.currentSchool = currentSchool;
    }

    public Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    public void setCurrentTeacher(Teacher currentTeacher) {
        this.currentTeacher = currentTeacher;
    }







    //Funciona genial sin MutableLiveData, lo echo 0 de menos

    /* Se pasa instancia del callback propio como parámetro. Así, para hacer una operación
     * solo se necesita hacer una llamada, despreocupándose por los "Observers" que puedan quedar colgados.
     *
     * Parece ser que MutableLiveData almacena cada Observer que se declara en Observe y los mantiene vivos,
     * pudiendo provocar errores en interfaces por referencias a contextos nulos de los fragmentos.
     * Una posible solución es preguntando en el Observe si alguno de los elementos de la interfaz es nulo para
     * evitar ese problema, pero de todas formas, esos Observers se quedan cargados en RAM ocupando espacio.
     */


    /* ---------- SCHOOLS ---------- */

    public void loadAllSchools(OnSchoolsResponse observer){
        Call<ArrayList<School>> request = schoolClient.getAllSchools();

        request.enqueue(new Callback<ArrayList<School>>() {
            @Override
            public void onResponse(Call<ArrayList<School>> call, Response<ArrayList<School>> response) {
                if(response.body() == null) {
                    observer.onFailure();
                }else{
                    observer.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<School>> call, Throwable t) {
                observer.onFailure();
            }
        });
    }


    public void addSchool(School school, OnDBResponse observer){
        Call<DBResponse> request = schoolClient.addSchool(school);

        request.enqueue(new Callback<DBResponse>() {
            @Override
            public void onResponse(Call<DBResponse> call, Response<DBResponse> response) {
                if(response.body() == null || response.body().getResult() == false){
                    observer.onFailure();
                }else{
                    observer.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<DBResponse> call, Throwable t) {
                observer.onFailure();
                Log.v("xyzyx", t.getMessage());
            }
        });
    }



    public void updateSchool(School school, OnDBResponse observer){
        Call<DBResponse> request = schoolClient.updateSchool(school.getId(), school);

        request.enqueue(new Callback<DBResponse>() {
            @Override
            public void onResponse(Call<DBResponse> call, Response<DBResponse> response) {
                if(response.body() == null || response.body().getResult() == false){
                    observer.onFailure();
                }else{
                    observer.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<DBResponse> call, Throwable t) {
                observer.onFailure();
            }
        });
    }



    public void deleteSchool(School school, OnDBResponse observer){
        Call<DBResponse> request = schoolClient.deleteSchool(school.getId());

        request.enqueue(new Callback<DBResponse>() {
            @Override
            public void onResponse(Call<DBResponse> call, Response<DBResponse> response) {
                if(response.body() == null || response.body().getResult() == false){
                    observer.onFailure();
                }else{
                    observer.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<DBResponse> call, Throwable t) {
                observer.onFailure();
            }
        });
    }






    /* ---------- TEACHERS ---------- */


    public void loadAllTeachers(OnTeachersResponse observer){
        Call<ArrayList<Teacher>> request = teacherClient.getAllTeachers();

        request.enqueue(new Callback<ArrayList<Teacher>>() {
            @Override
            public void onResponse(Call<ArrayList<Teacher>> call, Response<ArrayList<Teacher>> response) {
                if(response.body() == null) {
                    observer.onFailure();
                }else{
                    observer.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Teacher>> call, Throwable t) {
                observer.onFailure();
            }
        });
    }



    public void loadTeachersOf(School school, OnTeachersResponse observer){
        Call<ArrayList<Teacher>> request = teacherClient.getTeachersOf(school.getId());

        request.enqueue(new Callback<ArrayList<Teacher>>() {
            @Override
            public void onResponse(Call<ArrayList<Teacher>> call, Response<ArrayList<Teacher>> response) {
                if(response.body() == null) {
                    observer.onFailure();
                }else{
                    observer.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Teacher>> call, Throwable t) {
                observer.onFailure();
            }
        });
    }

    private void addTeacher(Teacher teacher, OnDBResponse observer){
        Call<DBResponse> request = teacherClient.addTeacher(teacher);

        request.enqueue(new Callback<DBResponse>() {
            @Override
            public void onResponse(Call<DBResponse> call, Response<DBResponse> response) {
                if(response.body() == null || response.body().getResult() == false){
                    observer.onFailure();
                }else{
                    observer.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<DBResponse> call, Throwable t) {
                observer.onFailure();
            }
        });
    }




    /* addTeacher( Uri: imageUri, Teacher: teacher)
     * Este método añade un profesor pasado por parámetro, pudiendo establecer también su imagen
     *
     * El parámetro imageUri corresponde a la imageUri que devuelve la actividad de haber seleccionado
     * una imagen de la galería. Para sacar un archivo File de esa URI con su mapa de bits, es necesario
     * hacerlo accediendo al almacenamiento externo, siempre comprobando que los permisos están concedidos.
     * El método getImageFileFromUri(Uri: imageUri) está definido en esta clase.
     * Cuando se obtiene el objeto File, hay que crear un Body de Multipart Form-Data, que es el tipo de
     * contenido que se acepta para subir imágenes. A continuación, se intenta subir al servidor.
     *
     * Cuando el servidor responde a la subida de la imagen, se comprueba si se ha guardado correctamente.
     * En caso de haberse guardado correctamente la imagen, devuelve la URL de la imagen pública, la cual
     * se usa para asignarla como propiedad al objeto Teacher. A continuación, se subiría al servidor el profesor
     * con su propiedad URL de la imagen bien puesta.
     *
     * En caso de no haberse guardado correctamente la imagen, se ignora ese campo y se intenta subir el profesor
     * sin haber asignado URL de imagen, y la base de datos coge el valor por defecto que se le ha configurado,
     * que es una URL a una imagen genérica.
     */
    public void addTeacher(Uri imageUri, Teacher teacher, OnDBResponse observer){
        if(imageUri == null || !storagePermissionIsGranted()){
            addTeacher(teacher, observer);
        }else{
            File imageFile = getImageFileFromUri(imageUri);
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(context.getContentResolver().getType(imageUri)),
                            imageFile
                    );

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

            Call<DBResponse> request = teacherClient.saveImage(body);

            request.enqueue(new Callback<DBResponse>() {
                @Override
                public void onResponse(Call<DBResponse> call, Response<DBResponse> response) {
                    if (response.body() != null && response.body().getResult() == true) {
                        //Si se ha subido la imagen correctamente, se asigna al objeto la URL de la imagen que se ha recibido como respuesta.
                        teacher.setPictureUrl(response.body().getUrl());
                    }
                    addTeacher(teacher, observer);
                }

                @Override
                public void onFailure(Call<DBResponse> call, Throwable t) {
                    addTeacher(teacher, observer);
                }
            });

        }
    }


    private void updateTeacher(Teacher teacher, OnDBResponse observer){
        Call<DBResponse> request = teacherClient.updateTeacher(teacher.getId(), teacher);

        request.enqueue(new Callback<DBResponse>() {
            @Override
            public void onResponse(Call<DBResponse> call, Response<DBResponse> response) {
                if(response.body() == null || response.body().getResult() == false){
                    Log.v("xyzyx", "RESPONSE MM 1");
                    observer.onFailure();
                }else{
                    observer.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<DBResponse> call, Throwable t) {
                Log.v("xyzyx", "RESPONSE MM 2");
                observer.onFailure();
            }
        });
    }





    public void updateTeacher(Uri imageUri, Teacher teacher, OnDBResponse observer){
        if(imageUri == null || !storagePermissionIsGranted()){
            updateTeacher(teacher, observer);
        }else{
            File imageFile = getImageFileFromUri(imageUri);
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(context.getContentResolver().getType(imageUri)),
                            imageFile
                    );

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

            Call<DBResponse> request = teacherClient.saveImage(body);

            request.enqueue(new Callback<DBResponse>() {
                @Override
                public void onResponse(Call<DBResponse> call, Response<DBResponse> response) {
                    if (response.body() != null && response.body().getResult() == true) {
                        teacher.setPictureUrl(response.body().getUrl());
                    }
                    updateTeacher(teacher, observer);
                }

                @Override
                public void onFailure(Call<DBResponse> call, Throwable t) {
                    updateTeacher(teacher, observer);
                }
            });

        }
    }


    public void deleteTeacher(Teacher teacher, OnDBResponse observer){
        Call<DBResponse> request = teacherClient.deleteTeacher(teacher.getId());

        request.enqueue(new Callback<DBResponse>() {
            @Override
            public void onResponse(Call<DBResponse> call, Response<DBResponse> response) {
                if(response.body() == null || response.body().getResult() == false){
                    observer.onFailure();
                }else{
                    observer.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<DBResponse> call, Throwable t) {
                observer.onFailure();
            }
        });
    }



    public boolean storagePermissionIsGranted(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M) { return true; }
        return context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }




    /* getImageFileFromUri(Uri: uri)
     * Devuelve un objeto File correspondiente al mapa de bits de la imagen de la URI pasada por parámetro.
     * Al abrir un intent de elegir una imagen de la galería, devuelve una URI "protegida", la cual sirve
     * para mostrarla por ejemplo en un ImageView, pero no para subirla a un servidor. Es por eso que se
     * necesita acceder al almacenamiento para obtener el archivo de mapa de bits de esa imagen.
     *
     * Nota: asegurar que los permisos de almacenamiento están concedidos antes de llamar a este método.
     */
    private File getImageFileFromUri(Uri uri){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return new File(picturePath);
    }


}
