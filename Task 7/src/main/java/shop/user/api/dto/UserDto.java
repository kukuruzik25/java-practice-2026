package shop.user.api.dto;

public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String profileDescripton;

    public UserDto(Integer id, String name, String email, String profileDescripton) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileDescripton = profileDescripton;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileDescripton() {
        return profileDescripton;
    }
}
