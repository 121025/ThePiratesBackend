package com.ThePirates.Backend;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class RequestController {
    private final MerchandiseRepository memberRepository;
    private final OptionsRepository optionsRepository;
    @Autowired
    EntityManager em;

    //점포 추가
    @PostMapping(value = "/register")
    public Request addStore(@RequestBody Request request) {
        MerchandiseTable member = MerchandiseTable.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getDelivery().getType())
                .closing(request.getDelivery().getClosing())
                .build();
        memberRepository.save(member);
        request.setId(member.getId());
        for(Options op: request.getOptions()) {
            OptionsTable optionsTable = OptionsTable.builder()
                    .name(op.getName())
                    .merch_id(request.getId())
                    .price(op.getPrice())
                    .stock(op.getStock())
                    .build();
            optionsRepository.save(optionsTable);
        }
        return request;
    }

    //상품 목록 조회
    @GetMapping(value = "/merchandise")
    public List<Info> getInfo() {
        List<Info> result = new ArrayList<>();
        List<Object[]> list = em.createNativeQuery("select m.name, m.description, o.price from merchandise_t m " +
                "inner join (select merch_id as id, min(price) as price from options_t group by merch_id) o " +
                "on m.id = o.id " +
                "order by m.id desc;").getResultList();
        for(Object[] o: list) {
            String price = NumberFormat.getInstance().format(o[2]) + " ~ ";
            Info info = Info.builder()
                    .name(String.valueOf(o[0]))
                    .description(String.valueOf(o[1]))
                    .price(price)
                    .build();
            result.add(info);
        }
        return result;
    }

    //상품 상세조회
    @GetMapping(value = "/merchandise/{id}")
    public DetailedInfo getDetailedInfo(@PathVariable("id") long id) {
        List<DetailedInfoOptions> options = new ArrayList<>();
        Object[] info = (Object[]) em.createNativeQuery("select name, description, type as delivery " +
                        "from merchandise_t where id = :id ;")
                .setParameter("id", id).getSingleResult();
        List<Object[]> optionsList = em.createNativeQuery("select name, price " +
                        "from options_t where merch_id = :id ;")
                .setParameter("id", id).getResultList();
        for(Object[] o: optionsList) {
            DetailedInfoOptions detailedInfoOptions = DetailedInfoOptions.builder()
                    .name(String.valueOf(o[0]))
                    .price((int) o[1])
                    .build();
            options.add(detailedInfoOptions);
        }
        DetailedInfo detailedInfo = DetailedInfo.builder()
                .name(String.valueOf(info[0]))
                .description(String.valueOf(info[1]))
                .delivery(String.valueOf(info[2]))
                .options(options)
                .build();
        return detailedInfo;
    }

    //수령일 선택 목록
    @GetMapping(value = "/delivery/{id}")
    public List<Date> getDate(@PathVariable("id") long id) {
        SimpleDateFormat format = new SimpleDateFormat("M일 d일");
        String[] dayOfWeek = {"일", "월", "화", "수", "목", "금", "토"};
        int offset = 0;
        Calendar calendar = Calendar.getInstance();
        List<Date> result = new ArrayList<>();
        Object[] delivery = (Object[]) em.createNativeQuery("select type, closing " +
                        "from merchandise_t where id = :id ;")
                .setParameter("id", id).getSingleResult();
        if(String.valueOf(delivery[0]) == "regular") {
            offset++;
        }
        LocalTime now = LocalTime.now();
        LocalTime closing = LocalTime.parse(String.valueOf(delivery[1]));
        if(now.isAfter(closing)) {
            offset++;
        }
        calendar.add(Calendar.DATE, offset);
        for(int i = 0; i < 5; i++) {
            while(calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                calendar.add(Calendar.DATE, 1);
            }
            calendar.add(Calendar.DATE, 1);
            String dateString = new SimpleDateFormat("M일 d일 ").format(calendar.getTime());
            dateString += dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1] + "요일";
            Date date = Date.builder()
                    .date(dateString)
                    .build();
            result.add(date);
        }
        return result;
    }

    //점포 삭제
    @Transactional
    @DeleteMapping(value = "/delete/{id}")
    public String deleteStore(@PathVariable("id") long id) {
        em.createNativeQuery("delete from options_t where merch_id = :id ;" +
                "delete from merchandise_t where id = :id ;")
                .setParameter("id", id).executeUpdate();
        return "Deleted store with ID: " + id;
    }
}
