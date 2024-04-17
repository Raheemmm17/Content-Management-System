package com.example.cms.serviceIMPL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.UserDTO.ContributionPanelResponce;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PannelNotFoundByIdException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.ContributionPanel;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.Panelrepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PanelServiceIMPL implements PanelService{
	private Panelrepository contributionRepository;
	private UserRepository userRepository;
	private ResponseStructure<ContributionPanelResponce> panelStructure;

	private BlogRepository blogRepository;

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponce>> addUserToContributionPanel(int userId, int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner->{
			return contributionRepository.findById(panelId).map(panel->{
				if(!blogRepository.existsByUserAndContributionPanel(owner,panel))
					throw new IllegalAccessRequestException("Failed To Add Contributior");
				return userRepository.findById(userId).map(contributor->{
					panel.getContributors() .add(contributor);
					contributionRepository.save(panel);	

					return ResponseEntity.ok(panelStructure.setStatusCode(HttpStatus.OK.value())
							.setMessage("Contributor set Successfully")
							.setData(mapToPanelResponse(panel)));

				}).orElseThrow(()-> new UserNotFoundByIdException("Entered User Id is not Found"));
			}).orElseThrow(()-> new PannelNotFoundByIdException("Entered Panel Id is not found"));
		}).get();
	}

	private ContributionPanelResponce mapToPanelResponse(ContributionPanel panel) {
		ContributionPanelResponce responce = new ContributionPanelResponce();
		responce.setContributors(panel.getContributors());
		responce.setPanelId(panel.getPanelId());
		return responce;
	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponce>> removeUserFromContributionPanel(int userId, int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner -> {
			return contributionRepository.findById(panelId).map(panel ->{
				if(!blogRepository.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("Failed to remove Contributor");

				return userRepository.findById(userId).map(contributor ->{
					if(panel.getContributors().remove(contributor)) {
						contributionRepository.save(panel);
						return ResponseEntity.ok(panelStructure.setStatusCode(HttpStatus.OK.value())
								.setMessage("Contributor Removed Successfully")
								.setData(mapToPanelResponse(panel)));
					}
					else 
						throw new UserNotFoundByIdException("Entered User Id is Not Found in the Contribution Panel");

				}).orElseThrow(()-> new UserNotFoundByIdException("Entered User Id is Not Found "));
			}).orElseThrow(()-> new PannelNotFoundByIdException("Entered panel Id is not Found"));
		}).get();
	}
}
