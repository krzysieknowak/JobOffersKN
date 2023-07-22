package pl.joboffers;

public interface TestOffersDto {

    default String bodyWithZeroOffers(){
        return "[]";
    }

    default String bodyWithOneOffer(){
        return """
                [{
                        "title": "Junior Java Developer",
                        "company": "BlueSoft Sp. z o.o.",
                        "salary": "7 000 – 9 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
                    }]
                """.trim();
    }

    default String bodyWithFiveOffers(){
        return """
                [{
                        "title": "Java (CMS) Developer",
                        "company": "Efigence SA",
                        "salary": "16 000 – 18 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
                    },
                    {
                        "title": "Junior Java Developer",
                        "company": "Sollers Consulting",
                        "salary": "7 500 – 11 500 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc"
                    },
                    {
                        "title": "Junior Full Stack Developer",
                        "company": "Vertabelo S.A.",
                        "salary": "7 000 – 9 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-full-stack-developer-vertabelo-remote-k7m9xpnm"
                    },
                    {
                        "title": "Junior Java Developer",
                        "company": "NIX Tech Kft.",
                        "salary": "6 169 – 12 339 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-nix-tech-kft-budapest-d1wuebdj"
                    },
                    {
                        "title": "2023 Technology Program BNY Mellon",
                        "company": "BNY Mellon",
                        "salary": "5 833 – 7 500 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/2023-technology-program-bny-mellon-bny-mellon-remote-ezutwncf"
                    }]
                """.trim();
    }
    default String bodyWithTwoOffers(){
        return """
                [{
                        "title": "Java (CMS) Developer",
                        "company": "Efigence SA",
                        "salary": "16 000 – 18 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
                    },
                    {
                        "title": "Junior Java Developer",
                        "company": "Sollers Consulting",
                        "salary": "7 500 – 11 500 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc"
                    }]
                """.trim();
    }
    default String bodyWithNextTwoOffers(){
        return """
                [{
                        "title": "Junior Java Developer",
                        "company": "NIX Tech Kft.",
                        "salary": "6 169 – 12 339 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-nix-tech-kft-budapest-d1wuebdj"
                    },
                    {
                        "title": "2023 Technology Program BNY Mellon",
                        "company": "BNY Mellon",
                        "salary": "5 833 – 7 500 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/2023-technology-program-bny-mellon-bny-mellon-remote-ezutwncf"
                    }]
                """.trim();
    }
}
