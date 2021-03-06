package com.example.demo.impls;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customRepositories.SeatStatusCustomRepository;
import com.example.demo.entities.SeatStatusEntity;

@SuppressWarnings("rawtypes")
@Repository
@Transactional
public class SeatStatusRepositoryImpl implements SeatStatusCustomRepository {

	@Autowired
	EntityManager entityManager;

	// 予約確定かを調査する
	@SuppressWarnings("unchecked")
	@Override
	public List<SeatStatusEntity> findIfAlreadyReserved(Date date, String hour, String studentcode) {
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND t09_student_code = :studentcode AND t09_checkin_flag='1'";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);

		return query.getResultList();
	}

	// 仮予約かを調査する
	@SuppressWarnings("unchecked")
	@Override
	public List<SeatStatusEntity> findIfAlreadyTentativelyReserved(Date date, String hour, String studentcode) {
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND t09_student_code = :studentcode AND t09_checkin_flag='0'";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);

		return query.getResultList();
	}

	// 予約リストを取得する
	@SuppressWarnings("unchecked")
	@Override
	public List<SeatStatusEntity> getReservations(Date date, String hour, String studentcode) {
		String jpql = "SELECT * FROM t09_seat_status WHERE ((t09_date = :date AND t09_checkin_hour >= :hour) OR (t09_date > :date)) AND t09_student_code = :studentcode AND t09_checkin_flag = '1' ORDER BY t09_date ASC, t09_checkin_hour ASC;";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);

		return query.getResultList();
	}

	// 予約IDを基に予約を取得する
	@SuppressWarnings("unchecked")
	@Override
	public SeatStatusEntity getReservationByNumber(Date date, String hour, String studentcode, int reservationNumber) {
		String jpql = "SELECT * FROM t09_seat_status WHERE ((t09_date = :date AND t09_checkin_hour >= :hour) OR (t09_date > :date)) AND t09_student_code = :studentcode AND t09_number = :reservationNumber AND t09_checkin_flag = '1' ORDER BY t09_date ASC, t09_checkin_hour ASC;";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);
		query.setParameter("reservationNumber", reservationNumber);

		return query.getSingleResult();
	}

	// 座席がユーザにより利用中かチェックする
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIfSeatIsUsingByMyself(Date date, String hour, String machineCode, String machineNumber,
			String studentcode) {
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND t09_machine_code = :machineCode AND t09_machine_no = :machineNumber AND t09_student_code = :studentcode AND t09_machine_count = 1";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("machineCode", machineCode);
		query.setParameter("machineNumber", machineNumber);
		query.setParameter("studentcode", studentcode);

		if (query.getResultList().size() > 0) {
			return true;
		}
		return false;
	}

	// 座席が状態コードのような状態になっているかどうかチェックする(状態コード -> 0:仮予約中 1:予約中 2:利用中)
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIfSeatIsInStateByStatusCode(Date date, String hour, String machineCode, String machineNumber,
			String checkinFlag) {
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND t09_machine_code = :machineCode AND t09_machine_no = :machineNumber AND t09_checkin_flag = :checkinFlag";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("machineCode", machineCode);
		query.setParameter("machineNumber", machineNumber);
		query.setParameter("checkinFlag", checkinFlag);

		if (query.getResultList().size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIfUserAlreadyReserved(String date, String hour, String studentcode, String checkinFlag, String checkinFlag2) {
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND (t09_checkin_flag = :checkinFlag OR t09_checkin_flag = :checkinFlag2) AND t09_student_code = :studentcode";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);
		query.setParameter("checkinFlag", checkinFlag);
		query.setParameter("checkinFlag2", checkinFlag2);

		if (query.getResultList().size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIfSeatIsReservedByMyself(String date, String hour, String machineCode, String machineNumber, String studentCode, String checkinFlag, String checkinFlag2) {
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND t09_machine_code = :machineCode AND t09_machine_no = :machineNumber AND t09_student_code = :studentCode AND (t09_checkin_flag = :checkinFlag OR t09_checkin_flag = :checkinFlag2)";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("machineCode", machineCode);
		query.setParameter("machineNumber", machineNumber);
		query.setParameter("studentCode", studentCode);
		query.setParameter("checkinFlag", checkinFlag);
		query.setParameter("checkinFlag2", checkinFlag2);

		if (query.getResultList().size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIfSeatIsReservedByOthers(String date, String hour, String machineCode, String machineNumber, String studentCode, String checkinFlag, String checkinFlag2) {
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND t09_machine_code = :machineCode AND t09_machine_no = :machineNumber AND t09_student_code <> :studentCode AND (t09_checkin_flag = :checkinFlag OR t09_checkin_flag = :checkinFlag2)";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("machineCode", machineCode);
		query.setParameter("machineNumber", machineNumber);
		query.setParameter("studentCode", studentCode);
		query.setParameter("checkinFlag", checkinFlag);
		query.setParameter("checkinFlag2", checkinFlag2);

		if (query.getResultList().size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 状態に合わせて予約リストを表示する(状態コード -> 0:仮予約中 1:予約中 2:利用中)
	@SuppressWarnings("unchecked")
	@Override
	public List<SeatStatusEntity> getReservationByFlag(Date date, String hour, String studentcode, String checkinFlag) {
		String jpql = "SELECT * FROM t09_seat_status WHERE t09_date = :date AND t09_checkin_hour = :hour AND t09_student_code = :studentcode AND t09_checkin_flag = :checkinFlag";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("studentcode", studentcode);
		query.setParameter("checkinFlag", checkinFlag);

		return query.getResultList();
	}

	// 予約中のマシンの台数を取得
	@SuppressWarnings("unchecked")
	@Override
	public int countReservedMachine(Date date, String hour, String machinecode) {
		String jpql = "SELECT * FROM t09_seat_status x WHERE x.t09_date = :date AND x.t09_checkin_hour = :hour AND x.t09_machine_code = :machinecode AND (x.t09_checkin_flag = '1' OR x.t09_checkin_flag = '2' OR x.t09_checkin_flag = '4')";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("machinecode", machinecode);

		return query.getResultList().size();
	}

	// 予約中のマシンの台数を取得(継続利用用)
	@SuppressWarnings("unchecked")
	@Override
	public int countReuseReservedMachine(String date, String hour, String machinecode) {
		String jpql = "SELECT * FROM t09_seat_status x WHERE x.t09_date = :date AND x.t09_checkin_hour = :hour AND x.t09_machine_code = :machinecode AND (x.t09_checkin_flag = '1' OR x.t09_checkin_flag = '2' OR x.t09_checkin_flag = '4')";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("date", date);
		query.setParameter("hour", hour);
		query.setParameter("machinecode", machinecode);

		return query.getResultList().size();
	}

	// 利用開始している機種を取得(継続利用用)
	@SuppressWarnings("unchecked")
	@Override
	public List<SeatStatusEntity> getReservationForReusing(String studentcode, String checkinFlag, String hour, Date date) {
		String jpql = "SELECT * FROM t09_seat_status x WHERE x.t09_student_code = :studentcode AND x.t09_checkin_flag = :checkinFlag AND x.t09_date = :date AND x.t09_checkin_hour = :hour";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("studentcode", studentcode);
		query.setParameter("checkinFlag", checkinFlag);
		query.setParameter("date", date);
		query.setParameter("hour", hour);

		return query.getResultList();
	}

	// 機種コードで利用している機種を取得
	@SuppressWarnings("unchecked")
	@Override
	public SeatStatusEntity getReservationByMachinecode(String machinecode, String studentcode,
			String checkinFlag, String hour) {
		String jpql = "SELECT * FROM t09_seat_status x WHERE x.t09_student_code = :studentcode AND x.t09_checkin_flag = :checkinFlag AND t09_machine_code = :machinecode AND t09_checkin_hour = :hour";

		TypedQuery<SeatStatusEntity> query = (TypedQuery<SeatStatusEntity>) entityManager.createNativeQuery(jpql,
				SeatStatusEntity.class);
		query.setParameter("machinecode", machinecode);
		query.setParameter("studentcode", studentcode);
		query.setParameter("checkinFlag", checkinFlag);
		query.setParameter("hour", hour);

		return query.getSingleResult();
	}
}