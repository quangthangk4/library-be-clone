package com.library.catalog.presentation.controller;

import com.library.catalog.application.GetListPublicationForLibrarianUseCase;
import com.library.catalog.application.GetPublicationByIdByLibrarianUseCase;
import com.library.catalog.application.UpdatePublicationUseCase;
import com.library.catalog.application.CreatePublicationUseCase;
import com.library.catalog.dto.request.publication.CreatePublicationRequest;
import com.library.catalog.dto.request.publication.PublicationSearchRequest;
import com.library.catalog.dto.request.publication.UpdatePublicationRequest;
import com.library.catalog.dto.response.publication.LibrarianPublicationDetailResponse;
import com.library.catalog.dto.response.publication.LibrarianPublicationListResponse;
import com.library.catalog.dto.response.publication.UpdatePublicationResponse;
import com.library.shared.constant.RoleConstants;
import com.library.shared.dto.ApiResponseApp;
import com.library.shared.dto.PageResponse;
import com.library.shared.util.RequiresRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
public class PublicationController {

    private final GetListPublicationForLibrarianUseCase getListPublicationForLibrarianUseCase;
    private final GetPublicationByIdByLibrarianUseCase getPublicationByIdByLibrarianUseCase;
    private final UpdatePublicationUseCase updatePublicationUseCase;
    private final CreatePublicationUseCase createPublicationUseCase;

    @PostMapping
    @RequiresRole(RoleConstants.LIBRARIAN)
    public ApiResponseApp<Void> createPublication(@RequestBody @Valid CreatePublicationRequest request) {
        log.info("Create publication with request: {}", request);
        createPublicationUseCase.execute(request);
        return ApiResponseApp.success("create publication success");
    }


    @PutMapping("/{id}")
    @RequiresRole(RoleConstants.LIBRARIAN)
    public ApiResponseApp<Void> updatePublication(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdatePublicationRequest request) {
        log.info("Update publication with request: {}", request);
        updatePublicationUseCase.execute(id, request);
        return ApiResponseApp.success("update success");
    }

    // not finish
    @PutMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequiresRole(RoleConstants.LIBRARIAN)
    public ApiResponseApp<String> updatePublicationCover(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        log.info("Update publication cover with id: {}, file: {}", id, file);
        return ApiResponseApp.success("update publication cover success", "https://scontent.fhan3-4.fna.fbcdn.net/v/t39.30808-1/307986850_1310565733103522_5247040603385687805_n.jpg?stp=c0.0.1536.1536a_dst-jpg_s200x200_tt6&_nc_cat=104&ccb=1-7&_nc_sid=e99d92&_nc_eui2=AeF-9mXKwwe1p3pcx3NYuWxWfKaGmN7xaId8poaY3vFoh2_dLaWdbKGs1YDndn3ei_UVZk6RoTSgGQ0PZaoC1TjK&_nc_ohc=AZOdnzAgaGcQ7kNvwEG6vFg&_nc_oc=AdoSpfjW8_67ijQoXJuastV9GFFl6PBY0sPrcHKA7xZslL1hAnURzBni1e2hGfOboGU&_nc_zt=24&_nc_ht=scontent.fhan3-4.fna&_nc_gid=YNF7ER-mNFrFd0_AbuD7cw&_nc_ss=7a3a8&oh=00_Af0srYOJMsJinSzbtNoElIiVNXSGmHhK6b2ZElApp8zFWg&oe=69DE7B68");
    }

    // not finish
    @PutMapping(value = "/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequiresRole(RoleConstants.LIBRARIAN)
    public ApiResponseApp<String> updatePublicationFile(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file) {
        log.info("Update publication file with id: {}, file: {}", id, file);
        String url = "https://corecppil.github.io/Meetups/2020-05-26_CoreCpp_Worldwide!/The_SOLID_Principles.pdf";
        return ApiResponseApp.success("update publication file success", url);
    }


    @GetMapping("/librarian/{id}")
    @RequiresRole(RoleConstants.LIBRARIAN)
    public ApiResponseApp<LibrarianPublicationDetailResponse> getPublicationByIdByLibrarian(@PathVariable("id") Long id) {
        return ApiResponseApp.success(getPublicationByIdByLibrarianUseCase.execute(id));
    }



    @RequiresRole(RoleConstants.LIBRARIAN)
    @GetMapping("/librarian")
    public ApiResponseApp<PageResponse<LibrarianPublicationListResponse>> getPublications(
            @ModelAttribute PublicationSearchRequest request
    ) {
        return ApiResponseApp.success(getListPublicationForLibrarianUseCase.execute(request));
    }

}
